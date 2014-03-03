# 13 "stdio.h"
 




 
# 1 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/_mingw.h" 1
# 16 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/_mingw.h" 3
 




 



 
# 1 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/stddef.h" 1



typedef unsigned long size_t;
typedef long ssize_t;
typedef unsigned short wchar_t;
typedef long ptrdiff_t;
typedef long intptr_t;
typedef unsigned long uintptr_t;



typedef signed char int8_t;
typedef signed short int int16_t;
typedef signed int int32_t;
typedef signed long long int int64_t;
typedef unsigned char uint8_t;
typedef unsigned short int uint16_t;
typedef unsigned int uint32_t;
typedef unsigned long long int uint64_t;





void *alloca(size_t size);
# 28 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/_mingw.h" 2
# 1 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/stdarg.h" 1
# 29 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/stdarg.h" 3
typedef char *va_list;
 






 
typedef va_list __gnuc_va_list;
# 29 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/_mingw.h" 2
# 81 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/_mingw.h" 3
 
# 91 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/_mingw.h" 3
typedef long __time32_t;

typedef long long __time64_t;


typedef __time32_t time_t;






typedef unsigned long size_t;

typedef long ssize_t;


typedef unsigned int wint_t;
typedef unsigned short wctype_t;

typedef unsigned short wchar_t;


typedef int errno_t;


typedef struct threadlocaleinfostruct *pthreadlocinfo;
typedef struct threadmbcinfostruct *pthreadmbcinfo;
typedef struct localeinfo_struct _locale_tstruct,*_locale_t;

 
# 20 "stdio.h" 2
# 32 "stdio.h"
 
# 41 "stdio.h"
 




 






 







 


 






 
# 83 "stdio.h"
 
# 100 "stdio.h"
 




 
# 126 "stdio.h"
 


typedef struct _iobuf
{
	char*	_ptr;
	int	_cnt;
	char*	_base;
	int	_flag;
	int	_file;
	int	_charbuf;
	int	_bufsiz;
	char*	_tmpfname;
} FILE;





 


extern FILE (*_imp___iob)[];	 
# 168 "stdio.h"
 
extern FILE* __attribute__((__cdecl__))  fopen (const char*, const char*);
extern FILE* __attribute__((__cdecl__)) 	freopen (const char*, const char*, FILE*);
extern int __attribute__((__cdecl__)) 	fflush (FILE*);
extern int __attribute__((__cdecl__)) 	fclose (FILE*);
 
extern int __attribute__((__cdecl__)) 	remove (const char*);
extern int __attribute__((__cdecl__)) 	rename (const char*, const char*);
extern FILE* __attribute__((__cdecl__)) 	tmpfile (void);
extern char* __attribute__((__cdecl__)) 	tmpnam (char*);


extern char* __attribute__((__cdecl__)) 	_tempnam (const char*, const char*);
extern int __attribute__((__cdecl__)) 	_rmtmp(void);
extern int __attribute__((__cdecl__)) 	_unlink (const char*);


extern char* __attribute__((__cdecl__)) 	tempnam (const char*, const char*);
extern int __attribute__((__cdecl__)) 	rmtmp(void);
extern int __attribute__((__cdecl__)) 	unlink (const char*);



extern int __attribute__((__cdecl__)) 	setvbuf (FILE*, char*, int, size_t);

extern void __attribute__((__cdecl__)) 	setbuf (FILE*, char*);






 



extern int __attribute__((__cdecl__))  __mingw_fprintf(FILE*, const char*, ...);
extern int __attribute__((__cdecl__))  __mingw_printf(const char*, ...);
extern int __attribute__((__cdecl__))  __mingw_sprintf(char*, const char*, ...);
extern int __attribute__((__cdecl__))  __mingw_snprintf(char*, size_t, const char*, ...);
extern int __attribute__((__cdecl__))  __mingw_vfprintf(FILE*, const char*, char*);
extern int __attribute__((__cdecl__))  __mingw_vprintf(const char*, char*);
extern int __attribute__((__cdecl__))  __mingw_vsprintf(char*, const char*, char*);
extern int __attribute__((__cdecl__))  __mingw_vsnprintf(char*, size_t, const char*, char*);
# 292 "stdio.h"
 
extern int __attribute__((__cdecl__))  fprintf (FILE*, const char*, ...);
extern int __attribute__((__cdecl__))  printf (const char*, ...);
extern int __attribute__((__cdecl__))  sprintf (char*, const char*, ...);
extern int __attribute__((__cdecl__))  vfprintf (FILE*, const char*, char*);
extern int __attribute__((__cdecl__))  vprintf (const char*, char*);
extern int __attribute__((__cdecl__))  vsprintf (char*, const char*, char*);





 



extern int __attribute__((__cdecl__))  __msvcrt_fprintf(FILE*, const char*, ...);
extern int __attribute__((__cdecl__))  __msvcrt_printf(const char*, ...);
extern int __attribute__((__cdecl__))  __msvcrt_sprintf(char*, const char*, ...);
extern int __attribute__((__cdecl__))  __msvcrt_vfprintf(FILE*, const char*, char*);
extern int __attribute__((__cdecl__))  __msvcrt_vprintf(const char*, char*);
extern int __attribute__((__cdecl__))  __msvcrt_vsprintf(char*, const char*, char*);




 
extern int __attribute__((__cdecl__))  _snprintf (char*, size_t, const char*, ...);
extern int __attribute__((__cdecl__))  _vsnprintf (char*, size_t, const char*, char*);
extern int __attribute__((__cdecl__))  _vscprintf (const char*, char*);
# 330 "stdio.h"
 
int __attribute__((__cdecl__))  snprintf (char *, size_t, const char *, ...);
int __attribute__((__cdecl__))  vsnprintf (char *, size_t, const char *, char*);

int __attribute__((__cdecl__))  vscanf (const char * __restrict__, char*);
int __attribute__((__cdecl__))  vfscanf (FILE * __restrict__, const char * __restrict__,
		     char*);
int __attribute__((__cdecl__))  vsscanf (const char * __restrict__,
		     const char * __restrict__, char*);





 

extern int __attribute__((__cdecl__)) 	fscanf (FILE*, const char*, ...);
extern int __attribute__((__cdecl__)) 	scanf (const char*, ...);
extern int __attribute__((__cdecl__)) 	sscanf (const char*, const char*, ...);


 

extern int __attribute__((__cdecl__)) 	fgetc (FILE*);
extern char* __attribute__((__cdecl__)) 	fgets (char*, int, FILE*);
extern int __attribute__((__cdecl__)) 	fputc (int, FILE*);
extern int __attribute__((__cdecl__)) 	fputs (const char*, FILE*);
extern char* __attribute__((__cdecl__)) 	gets (char*);
extern int __attribute__((__cdecl__)) 	puts (const char*);
extern int __attribute__((__cdecl__)) 	ungetc (int, FILE*);






 
extern int __attribute__((__cdecl__)) 	_filbuf (FILE*);
extern int __attribute__((__cdecl__)) 	_flsbuf (int, FILE*);



extern __inline__ int __attribute__((__cdecl__))  getc (FILE* __F)
{
  return (--__F->_cnt >= 0)
    ?  (int) (unsigned char) *__F->_ptr++
    : _filbuf (__F);
}

extern __inline__ int __attribute__((__cdecl__))  putc (int __c, FILE* __F)
{
  return (--__F->_cnt >= 0)
    ?  (int) (unsigned char) (*__F->_ptr++ = (char)__c)
    :  _flsbuf (__c, __F);
}

extern __inline__ int __attribute__((__cdecl__))  getchar (void)
{
  return (--(&(*_imp___iob) [0 ])->_cnt >= 0)
    ?  (int) (unsigned char) *(&(*_imp___iob) [0 ])->_ptr++
    : _filbuf ((&(*_imp___iob) [0 ]));
}

extern __inline__ int __attribute__((__cdecl__))  putchar(int __c)
{
  return (--(&(*_imp___iob) [1 ])->_cnt >= 0)
    ?  (int) (unsigned char) (*(&(*_imp___iob) [1 ])->_ptr++ = (char)__c)
    :  _flsbuf (__c, (&(*_imp___iob) [1 ]));}
# 410 "stdio.h"
 

extern size_t __attribute__((__cdecl__)) 	fread (void*, size_t, size_t, FILE*);
extern size_t __attribute__((__cdecl__)) 	fwrite (const void*, size_t, size_t, FILE*);



 

extern int __attribute__((__cdecl__)) 	fseek (FILE*, long, int);
extern long __attribute__((__cdecl__)) 	ftell (FILE*);
extern void __attribute__((__cdecl__)) 	rewind (FILE*);
# 453 "stdio.h"
 

typedef long long fpos_t;




extern int __attribute__((__cdecl__)) 	fgetpos	(FILE*, fpos_t*);
extern int __attribute__((__cdecl__)) 	fsetpos (FILE*, const fpos_t*);



 

extern int __attribute__((__cdecl__)) 	feof (FILE*);
extern int __attribute__((__cdecl__)) 	ferror (FILE*);
# 480 "stdio.h"
extern void __attribute__((__cdecl__)) 	clearerr (FILE*);
extern void __attribute__((__cdecl__)) 	perror (const char*);





 
extern FILE* __attribute__((__cdecl__)) 	_popen (const char*, const char*);
extern int __attribute__((__cdecl__)) 	_pclose (FILE*);


extern FILE* __attribute__((__cdecl__)) 	popen (const char*, const char*);
extern int __attribute__((__cdecl__)) 	pclose (FILE*);




 
extern int __attribute__((__cdecl__)) 	_flushall (void);
extern int __attribute__((__cdecl__)) 	_fgetchar (void);
extern int __attribute__((__cdecl__)) 	_fputchar (int);
extern FILE* __attribute__((__cdecl__)) 	_fdopen (int, const char*);
extern int __attribute__((__cdecl__)) 	_fileno (FILE*);
extern int __attribute__((__cdecl__)) 	_fcloseall (void);
extern FILE* __attribute__((__cdecl__)) 	_fsopen (const char*, const char*, int);

extern int __attribute__((__cdecl__)) 	_getmaxstdio (void);
extern int __attribute__((__cdecl__)) 	_setmaxstdio (int);
# 522 "stdio.h"
extern int __attribute__((__cdecl__)) 	fgetchar (void);
extern int __attribute__((__cdecl__)) 	fputchar (int);
extern FILE* __attribute__((__cdecl__)) 	fdopen (int, const char*);
extern int __attribute__((__cdecl__)) 	fileno (FILE*);
# 1 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/sys/types.h" 1




 
# 50 "c:/users/braden steffaniak/documents/github/corndog/tcc/include/sys/types.h" 3
typedef unsigned short _ino_t;

typedef unsigned short ino_t;





typedef unsigned int _dev_t;

typedef unsigned int dev_t;






typedef int	_pid_t;





typedef _pid_t	pid_t;





typedef unsigned short _mode_t;


typedef _mode_t	mode_t;







  typedef long _off_t;

  typedef long off_t;






  typedef long long _off64_t;

  typedef long long off64_t;





struct timespec {
  time_t  tv_sec;    
  long    tv_nsec;   
};

struct itimerspec {
  struct timespec  it_interval;   
  struct timespec  it_value;      
};
# 535 "stdio.h" 2
extern __inline__ FILE* __attribute__((__cdecl__))  fopen64 (const char* filename, const char* mode)
{
  return fopen (filename, mode); 
}

int __attribute__((__cdecl__))  fseeko64 (FILE*, off64_t, int);






extern __inline__ off64_t __attribute__((__cdecl__))  ftello64 (FILE * stream)
{
  fpos_t pos;
  if (fgetpos(stream, &pos))
    return  -1LL;
  else
   return ((off64_t) pos);
}




 


 
extern int __attribute__((__cdecl__)) 	fwprintf (FILE*, const wchar_t*, ...);
extern int __attribute__((__cdecl__)) 	wprintf (const wchar_t*, ...);
extern int __attribute__((__cdecl__)) 	_snwprintf (wchar_t*, size_t, const wchar_t*, ...);
extern int __attribute__((__cdecl__)) 	vfwprintf (FILE*, const wchar_t*, char*);
extern int __attribute__((__cdecl__)) 	vwprintf (const wchar_t*, char*);
extern int __attribute__((__cdecl__)) 	_vsnwprintf (wchar_t*, size_t, const wchar_t*, char*);
extern int __attribute__((__cdecl__)) 	_vscwprintf (const wchar_t*, char*);
extern int __attribute__((__cdecl__)) 	fwscanf (FILE*, const wchar_t*, ...);
extern int __attribute__((__cdecl__)) 	wscanf (const wchar_t*, ...);
extern int __attribute__((__cdecl__)) 	swscanf (const wchar_t*, const wchar_t*, ...);
extern wint_t __attribute__((__cdecl__)) 	fgetwc (FILE*);
extern wint_t __attribute__((__cdecl__)) 	fputwc (wchar_t, FILE*);
extern wint_t __attribute__((__cdecl__)) 	ungetwc (wchar_t, FILE*);

 

extern int __attribute__((__cdecl__)) 	swprintf (wchar_t*, const wchar_t*, ...);
extern int __attribute__((__cdecl__)) 	vswprintf (wchar_t*, const wchar_t*, char*);



extern wchar_t* __attribute__((__cdecl__))  fgetws (wchar_t*, int, FILE*);
extern int __attribute__((__cdecl__)) 	fputws (const wchar_t*, FILE*);
extern wint_t __attribute__((__cdecl__)) 	getwc (FILE*);
extern wint_t __attribute__((__cdecl__)) 	getwchar (void);
extern wchar_t* __attribute__((__cdecl__))  _getws (wchar_t*);
extern wint_t __attribute__((__cdecl__)) 	putwc (wint_t, FILE*);
extern int __attribute__((__cdecl__)) 	_putws (const wchar_t*);
extern wint_t __attribute__((__cdecl__)) 	putwchar (wint_t);
extern FILE* __attribute__((__cdecl__)) 	_wfdopen(int, const wchar_t *);
extern FILE* __attribute__((__cdecl__)) 	_wfopen (const wchar_t*, const wchar_t*);
extern FILE* __attribute__((__cdecl__)) 	_wfreopen (const wchar_t*, const wchar_t*, FILE*);
extern FILE* __attribute__((__cdecl__)) 	_wfsopen (const wchar_t*, const wchar_t*, int);
extern wchar_t* __attribute__((__cdecl__))  _wtmpnam (wchar_t*);
extern wchar_t* __attribute__((__cdecl__))  _wtempnam (const wchar_t*, const wchar_t*);
extern int __attribute__((__cdecl__)) 	_wrename (const wchar_t*, const wchar_t*);
extern int __attribute__((__cdecl__)) 	_wremove (const wchar_t*);
extern void __attribute__((__cdecl__)) 	_wperror (const wchar_t*);
extern FILE* __attribute__((__cdecl__)) 	_wpopen (const wchar_t*, const wchar_t*);



int __attribute__((__cdecl__))  snwprintf (wchar_t* s, size_t n, const wchar_t*  format, ...);
int __attribute__((__cdecl__))  vsnwprintf (wchar_t* s, size_t n, const wchar_t* format, char* arg);

extern __inline__ int __attribute__((__cdecl__)) 
vsnwprintf (wchar_t* s, size_t n, const wchar_t* format, char* arg)
  { return _vsnwprintf ( s, n, format, arg);}

int __attribute__((__cdecl__))  vwscanf (const wchar_t * __restrict__, char*);
int __attribute__((__cdecl__))  vfwscanf (FILE * __restrict__,
		       const wchar_t * __restrict__, char*);
int __attribute__((__cdecl__))  vswscanf (const wchar_t * __restrict__,
		       const wchar_t * __restrict__, char*);
# 625 "stdio.h"
extern FILE* __attribute__((__cdecl__)) 	wpopen (const wchar_t*, const wchar_t*);





 
extern wint_t __attribute__((__cdecl__)) 	_fgetwchar (void);
extern wint_t __attribute__((__cdecl__)) 	_fputwchar (wint_t);
extern int __attribute__((__cdecl__)) 	_getw (FILE*);
extern int __attribute__((__cdecl__)) 	_putw (int, FILE*);


extern wint_t __attribute__((__cdecl__)) 	fgetwchar (void);
extern wint_t __attribute__((__cdecl__)) 	fputwchar (wint_t);
extern int __attribute__((__cdecl__)) 	getw (FILE*);
extern int __attribute__((__cdecl__)) 	putw (int, FILE*);
