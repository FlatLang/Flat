#include "ExceptionData.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <stdio.h>
#include <setjmp.h>
#include "ArrayList.h"

ExceptionData* new_ExceptionData(ExceptionData* __FATHOM__exception_data, jmp_buf* buf);
void del_ExceptionData(ExceptionData** __o__, ExceptionData* __FATHOM__exception_data);
static ArrayList* __FATHOM__getCodes(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__addCode(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static jmp_buf* __FATHOM__getBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static ExceptionData* __FATHOM__getCorrectData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static jmp_buf* __FATHOM__getCorrectBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static void __FATHOM__jumpToBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static ExceptionData* __FATHOM__getParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__setParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, ExceptionData* p);

PRIVATE
(
	jmp_buf* buffer;
	ExceptionData* parent;
	ArrayList* codes;
)

ExceptionData* new_ExceptionData(ExceptionData* __FATHOM__exception_data, jmp_buf* buf)
{
	NEW(ExceptionData, __o__);
	
	__o__->getCodes = __FATHOM__getCodes;
	__o__->addCode = __FATHOM__addCode;
	__o__->getBuffer = __FATHOM__getBuffer;
	__o__->getCorrectData = __FATHOM__getCorrectData;
	__o__->getCorrectBuffer = __FATHOM__getCorrectBuffer;
	__o__->jumpToBuffer = __FATHOM__jumpToBuffer;
	__o__->getParent = __FATHOM__getParent;
	__o__->setParent = __FATHOM__setParent;
	
	__o__->prv->buffer = 0;
	__o__->prv->parent = 0;
	__o__->prv->codes = 0;
	{
		__o__->prv->buffer = buf;
		__o__->prv->codes = new_ArrayList(__FATHOM__exception_data);
	}
	
	return __o__;
}

void del_ExceptionData(ExceptionData** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	
	del_ExceptionData(&(*__o__)->prv->parent, __FATHOM__exception_data);
	del_ArrayList(&(*__o__)->prv->codes, __FATHOM__exception_data);
	free((*__o__)->prv);
	
	{
	}
	free(*__o__);
}

static ArrayList* __FATHOM__getCodes(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->codes;
}

static void __FATHOM__addCode(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
	__o__->prv->codes->add(__o__->prv->codes, __FATHOM__exception_data, code);
}

static jmp_buf* __FATHOM__getBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->buffer;
}

static ExceptionData* __FATHOM__getCorrectData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
	ExceptionData* data;
	
	data = __o__;
	while (data != 0)
	{
		ArrayList* list;
		int i;
		
		list = data->getCodes(data, __FATHOM__exception_data);
		for (i = 0; i < list->getSize(list, __FATHOM__exception_data); i++)
		{
			if (list->get(list, __FATHOM__exception_data, i) == code)
			{
				return data;
			}
		}
		if (data->getParent(data, __FATHOM__exception_data) == 0)
		{
			return data;
		}
		data = data->getParent(data, __FATHOM__exception_data);
	}
	return 0;
}

static jmp_buf* __FATHOM__getCorrectBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
	ExceptionData* data;
	
	data = __o__->getCorrectData(__o__, __FATHOM__exception_data, code);
	if (data == 0)
	{
		return 0;
	}
	return data->getBuffer(data, __FATHOM__exception_data);
}

static void __FATHOM__jumpToBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
	ExceptionData* data;
	jmp_buf* buf;
	
	data = __o__->getCorrectData(__o__, __FATHOM__exception_data, code);
	if (data->getParent(data, __FATHOM__exception_data) == 0)
	{
		code = 1;
	}
	buf = data->getBuffer(data, __FATHOM__exception_data);
	longjmp(*buf, code);
}

static ExceptionData* __FATHOM__getParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->parent;
}

static void __FATHOM__setParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, ExceptionData* p)
{
	__o__->prv->parent = p;
}
