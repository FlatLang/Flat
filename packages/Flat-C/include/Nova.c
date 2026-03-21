#include "Nova.h"

nova_primitive_Nova_Null* nova_null;
void* nova_garbageData;

/**
 * Similar to fgets(), but handles automatic reallocation of the buffer.
 * Only parameter is the input stream.
 * Return value is a string. Don't forget to free it.
 */
char* ufgets(FILE* stream)
{
    unsigned int maxlen = 128, size = 128;
    char* buffer = (char*)NOVA_MALLOC(sizeof(char) * maxlen);
    
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
void arrayCopy(void* dest, int dIndex, const void* src, int sIndex, int len, int destLen)
{
	uint8_t *udest = (uint8_t*) dest;
	uint8_t *usrc = (uint8_t*) src;
	size_t size = sizeof(dest);
	
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

void** nova_gen_array(void** array, int* dimensionSizes, int dimension, int dimensions, int size)
{
	int i;

	for (i = 0; i < dimensionSizes[dimension]; i++)
	{
		array[i] = NOVA_MALLOC(size * dimensionSizes[dimension]);

		if (dimension + 1 < dimensions)
		{
			nova_gen_array(array[i], dimensionSizes, dimension + 1, dimensions, size);
		}
	}

	return array;
}

void nova_free_array(void** array, int* dimensionSizes, int dimension, int dimensions, del_function function, nova_exception_Nova_ExceptionData* exceptionData)
{
	int i;

	for (i = 0; i < dimensionSizes[dimension]; i++)
	{
		if (dimension + 1 < dimensions)
		{
			nova_free_array(array[i], dimensionSizes, dimension + 1, dimensions, function, exceptionData);

			NOVA_FREE(array[i]);
		}
		else
		{
			function(array[i], exceptionData);
		}
	}
}

nova_funcStruct* nova_get_funcStruct1(void* func) {
    nova_funcStruct* value = NOVA_MALLOC(sizeof(nova_funcStruct));
    value->func = func;
    value->ref = nova_null;
    value->context = nova_null;
    
    return value;
}

nova_funcStruct* nova_get_funcStruct2(void* func, void* ref) {
    nova_funcStruct* value = NOVA_MALLOC(sizeof(nova_funcStruct));
    value->func = func;
    value->ref = ref;
    value->context = nova_null;
    
    return value;
}

nova_funcStruct* nova_get_funcStruct3(void* func, void* ref, void* context) {
    nova_funcStruct* value = NOVA_MALLOC(sizeof(nova_funcStruct));
    value->func = func;
    value->ref = ref;
    value->context = context;
    
    return value;
}