#include "ArrayList.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <stdlib.h>
#include <Fathom.h>

PRIVATE
(
	int fathom_count;
	int fathom_size;
	int* fathom_data;
)

ArrayList* fathom_ArrayList_ArrayList(ExceptionData* exceptionData)
{
	NEW(ArrayList, reference);
	
	reference->prv->fathom_count = 0;
	reference->prv->fathom_size = 0;
	reference->prv->fathom_data = 0;
	{
		fathom_ArrayList_increaseSize(reference, exceptionData);
	}
	
	return reference;
}

void fathom_del_ArrayList(ArrayList** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	
	free((*reference)->prv);
	
	{
	}
	free(*reference);
}

void fathom_ArrayList_add(ArrayList* reference, ExceptionData* exceptionData, int fathom_var_90)
{
	if (reference->prv->fathom_count + 1 >= reference->prv->fathom_size)
	{
		fathom_ArrayList_increaseSize(reference, exceptionData);
	}
	reference->prv->fathom_data[reference->prv->fathom_count] = fathom_var_90;
	reference->prv->fathom_count = reference->prv->fathom_count + 1;
}

void fathom_ArrayList_increaseSize(ArrayList* reference, ExceptionData* exceptionData)
{
	int* fathom_tmp_93;
	
	reference->prv->fathom_size = reference->prv->fathom_size + 3;
	fathom_tmp_93 = (int*)malloc(sizeof(int) * reference->prv->fathom_size);
	arrayCopy(fathom_tmp_93, 0, reference->prv->fathom_data, 0, reference->prv->fathom_count, reference->prv->fathom_size, sizeof(int));
	free(reference->prv->fathom_data);
	reference->prv->fathom_data = fathom_tmp_93;
}

int fathom_ArrayList_getSize(ArrayList* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_count;
}

int fathom_ArrayList_get(ArrayList* reference, ExceptionData* exceptionData, int fathom_index_99)
{
	return reference->prv->fathom_data[fathom_index_99];
}
