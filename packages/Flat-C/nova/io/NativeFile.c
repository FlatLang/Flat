#include "NativeFile.h"

#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <Nova.h>
#include <sys/types.h>
#include <stdio.h>
#include <assert.h>
#include <errno.h>
#include <stdlib.h>

void setMaxOpenFiles(int num)
{
	#ifdef __APPLE__
		struct rlimit rlp;
		
		getrlimit(RLIMIT_NOFILE, &rlp);
		rlp.rlim_cur = num;
		setrlimit(RLIMIT_NOFILE, &rlp);
	#elif defined(_WIN32)
		_setmaxstdio(num);
	#endif
}

int getMaxOpenFiles()
{
	#ifdef __APPLE__
		struct rlimit rlp;
		
		getrlimit(RLIMIT_NOFILE, &rlp);
		
		return rlp.rlim_max;
	#elif defined(_WIN32)
		return _getmaxstdio();
	#endif
	
	return 100;
}

/* Always add at least this many bytes when extending the buffer.  */
#define MIN_CHUNK 64

/* Read up to (and including) a TERMINATOR from STREAM into *LINEPTR
   + OFFSET (and null-terminate it). *LINEPTR is a pointer returned from
   malloc (or NULL), pointing to *N characters of space.  It is realloc'd
   as necessary.  Return the number of characters read (not including the
   null terminator), or -1 on error or EOF.  On a -1 return, the caller
   should check feof(), if not then errno has been set to indicate
   the error.  */

int nova_getstr(char** lineptr, size_t* n, FILE* stream, char terminator, int offset)
{
    int nchars_avail; /* Allocated but unused chars in *LINEPTR.  */
    char *read_pos;   /* Where we're reading into *LINEPTR. */

    if (!lineptr || !n || !stream) {
        errno = EINVAL;
        return -1;
    }

    if (!*lineptr) {
        *n = MIN_CHUNK;
        *lineptr = malloc(*n);
        if (!*lineptr) {
            errno = ENOMEM;
            return -1;
        }
    }

    nchars_avail = *n - offset;
    read_pos = *lineptr + offset;

    for (;;) {
        int save_errno;
        register int c = getc(stream);

        save_errno = errno;

        /* We always want at least one char left in the buffer, since we
           always (unless we get an error while reading the first char)
           NUL-terminate the line buffer.  */
        
        assert((int)(*lineptr + *n) == (int)(read_pos + nchars_avail));
        
        if (nchars_avail < 2) {
            if (*n > MIN_CHUNK)
                *n *= 2;
            else
                *n += MIN_CHUNK;

            nchars_avail = *n + *lineptr - read_pos;
            *lineptr = NOVA_REALLOC(*lineptr, *n);
            
            if (!*lineptr) {
                errno = ENOMEM;
                
                return -1;
            }
            
            read_pos = *n - nchars_avail + *lineptr;
            assert((int)(*lineptr + *n) == (int)(read_pos + nchars_avail));
        }

        if (ferror(stream)) {
            /* Might like to return partial line, but there is no
               place for us to store errno.  And we don't want to just
               lose errno.  */
            errno = save_errno;
            return -1;
        }

        if (c == EOF) {
        	/* Return partial line, if any.  */
            if (read_pos == *lineptr) {
                return -1;
            } else {
                break;
            }
        }

        *read_pos++ = c;
        nchars_avail--;

        if (c == terminator) {
            /* Return the line.  */
            break;
        }
    }

    /* Done - NUL terminate and return the number of chars read.  */
    *read_pos = '\0';

    return read_pos - (*lineptr + offset);
}

int nova_getline (char** lineptr, size_t* n, FILE* stream)
{
  return nova_getstr(lineptr, n, stream, '\n', 0);
}

int nova_read (char** lineptr, size_t* n, FILE* stream)
{
  return nova_getstr(lineptr, n, stream, '\0', 0);
}