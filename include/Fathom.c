#include <stdio.h>
#include <stdlib.h>
#include "Fathom.h"
 
/**
 * Similar to fgets(), but handles automatic reallocation of the buffer.
 * Only parameter is the input stream.
 * Return value is a string. Don't forget to free it.
 */
char* ufgets(FILE* stream)
{
    unsigned int maxlen = 128, size = 128;
    char* buffer = (char*)malloc(maxlen);
    
    if(buffer != NULL) /* NULL if malloc() fails */
    {
        int ch = EOF;
        int pos = 0;
 
        /* Read input one character at a time, resizing the buffer as necessary */
        while((ch = fgetc(stream)) != '\n' && ch != EOF && !feof(stream))
        {
            buffer[pos++] = ch;
            
            if(pos == size) /* Next character to be inserted needs more memory */
            {
                size = pos + maxlen;
                buffer = (char*)realloc(buffer, size);
            }
        }
        
        buffer[pos] = '\0'; /* Null-terminate the completed string */
    }

    return buffer;
}

void testp()
{
	printf("ASDFASDFASDF");
}