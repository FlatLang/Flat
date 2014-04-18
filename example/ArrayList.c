#include "ArrayList.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "String.h"
#include <stdlib.h>
#include <Fathom.h>

ArrayList* new_ArrayList(ExceptionData* __FATHOM__exception_data);
void del_ArrayList(ArrayList** __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__add(ArrayList* __o__, ExceptionData* __FATHOM__exception_data, int var);
static void __FATHOM__increaseSize(ArrayList* __o__, ExceptionData* __FATHOM__exception_data);
static int __FATHOM__getSize(ArrayList* __o__, ExceptionData* __FATHOM__exception_data);
static int __FATHOM__get(ArrayList* __o__, ExceptionData* __FATHOM__exception_data, int index);

PRIVATE
(
	int count;
	int size;
	int* data;
)

ArrayList* new_ArrayList(ExceptionData* __FATHOM__exception_data)
{
	NEW(ArrayList, __o__);
	
	__o__->add = __FATHOM__add;
	__o__->increaseSize = __FATHOM__increaseSize;
	__o__->getSize = __FATHOM__getSize;
	__o__->get = __FATHOM__get;
	
	__o__->prv->count = 0;
	__o__->prv->size = 0;
	__o__->prv->data = 0;
	{
		__o__->increaseSize(__o__, __FATHOM__exception_data);
	}
	
	return __o__;
}

void del_ArrayList(ArrayList** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	
	free((*__o__)->prv);
	
	{
	}
	free(*__o__);
}

static void __FATHOM__add(ArrayList* __o__, ExceptionData* __FATHOM__exception_data, int var)
{
	if (__o__->prv->count + 1 >= __o__->prv->size)
	{
		__o__->increaseSize(__o__, __FATHOM__exception_data);
	}
	__o__->prv->data[__o__->prv->count] = var;
	__o__->prv->count = __o__->prv->count + 1;
}

static void __FATHOM__increaseSize(ArrayList* __o__, ExceptionData* __FATHOM__exception_data)
{
	int* tmp;
	
	__o__->prv->size = __o__->prv->size + 3;
	tmp = (int*)malloc(sizeof(int) * __o__->prv->size);
	arrayCopy(tmp, 0, __o__->prv->data, 0, __o__->prv->count, __o__->prv->size, sizeof(int));
	free(__o__->prv->data);
	__o__->prv->data = tmp;
}

static int __FATHOM__getSize(ArrayList* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->count;
}

static int __FATHOM__get(ArrayList* __o__, ExceptionData* __FATHOM__exception_data, int index)
{
	return __o__->prv->data[index];
}
