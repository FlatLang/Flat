#include "precompiled.h"
#include "Nova.h"
/**
 * Similar to fgets(), but handles automatic reallocation of the buffer.
 * Only parameter is the input stream.
 * Return value is a string. Don't forget to free it.
 */
char* ufgets(FILE* stream)
{
    unsigned int maxlen = 128, size = 128;
    char* buffer = (char*)NOVA_MALLOC(maxlen);
    
    if (buffer != NULL) /* NULL if malloc() fails */
    {
        int ch  = EOF;
        int pos = 0;
 
        /* Read input one character at a time, resizing the buffer as necessary */
        while ((ch = fgetc(stream)) != '\n' && ch != EOF && !feof(stream))
        {
            buffer[pos++] = ch;
            
            if (pos == size) /* Next character to be inserted needs more memory */
            {
                size   = pos + maxlen;
                buffer = (char*)NOVA_REALLOC(buffer, size);
            }
        }
        
        buffer[pos] = '\0'; /* Null-terminate the completed string */
    }

    return buffer;
}

/**
 * @dest Destination array
 * @dIndex Index to which we will start copying
 * @src Source array
 * @sIndex Index from which we will start copying
 * @len Number of elements that will be copied from source array
 * @destArrLen The length of the destination array (hence C doesn't know any length info about passed arrays)
 * @size The size of the type of the array (ex: if the array of type long, put in this parameter sizeof(long))
 */
void arrayCopy(void* dest, int dIndex, const void* src, int sIndex, int len, int destLen, size_t size)
{
	uint8_t *udest = (uint8_t*) dest;
	uint8_t *usrc = (uint8_t*) src;
	dIndex  *= size;
	sIndex  *= size;
	len     *= size;
	destLen *= size;

	if (src != dest)
	{
		memcpy(&udest[dIndex], &usrc[sIndex], len);
	}
	else
	{
		if (dIndex > sIndex)
		{
			uint8_t *tmp = (uint8_t*) calloc(destLen, size);
			memcpy(tmp, &udest[dIndex], (destLen-dIndex));
			memcpy(&udest[dIndex], &usrc[sIndex], len);
			memcpy(&udest[dIndex+len], tmp, (destLen-dIndex));
			NOVA_FREE(tmp);
		}
		else if (sIndex > dIndex)
		{
			memcpy(&udest[dIndex], &usrc[sIndex], (destLen-sIndex)+1);
		}
		else
		{
			return;
		}
	}
}

long_long currentTimeMillis()
{
	struct timeb tmb;
	unsigned_long_long value;
 
	ftime(&tmb);
	/*printf("tmb.time     = %ld (seconds)\n", tmb.time);
	printf("tmb.millitm  = %d (mlliseconds)\n", tmb.millitm);*/

	value  = tmb.time;
	value *= 1000;
	value += tmb.millitm;

	return value;
}

void copy_string(char* target, char* source)
{
	int length = strlen(source);
	
	int i;
	
	for (i = 0; i < length; i++)
	{
		target[i] = source[i];
	}
	
	target[i] = '\0';
}
