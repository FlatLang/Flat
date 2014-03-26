#include "ExceptionData.h"
#include <setjmp.h>

ExceptionData* new_ExceptionData(ExceptionData* __FATHOM__exception_data, jmp_buf* buf, int* codes, int num);
void del_ExceptionData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static jmp_buf* getBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static jmp_buf* getCorrectBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static int getNumCodes(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static int* getExceptionCodes(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static ExceptionData* getParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static void setParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, ExceptionData* p);

PRIVATE
(
jmp_buf* buffer;
ExceptionData* parent;
int numCodes;
int* exceptionCodes;
)

ExceptionData* new_ExceptionData(ExceptionData* __FATHOM__exception_data, jmp_buf* buf, int* codes, int num)
{
NEW(ExceptionData, __o__);

__o__->getBuffer = getBuffer;
__o__->getCorrectBuffer = getCorrectBuffer;
__o__->getNumCodes = getNumCodes;
__o__->getExceptionCodes = getExceptionCodes;
__o__->getParent = getParent;
__o__->setParent = setParent;

__o__->prv->buffer = buf;
__o__->prv->exceptionCodes = codes;
__o__->prv->numCodes = num;

return __o__;
}

void del_ExceptionData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
if (!__o__)
{
return;
}

free(__o__->prv->buffer);
del_ExceptionData(__o__->prv->parent, __FATHOM__exception_data);
free(__o__->prv->exceptionCodes);
free(__o__->prv);

free(__o__);
}

static jmp_buf* getBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
return __o__->prv->buffer;
}

static jmp_buf* getCorrectBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
ExceptionData* data;
int* codes;
int i;

data = __o__;
i = 0;
while (data != 0)
{
codes = data->getExceptionCodes(data, __FATHOM__exception_data);
{
for (i = 0; i < data->getNumCodes(data, __FATHOM__exception_data); i = i + 1)
{
if (codes[i] == code)
{
return data->getBuffer(data, __FATHOM__exception_data);
}
}
}
data = data->getParent(data, __FATHOM__exception_data);
}
return __o__->prv->buffer;
}

static int getNumCodes(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
return __o__->prv->numCodes;
}

static int* getExceptionCodes(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
return __o__->prv->exceptionCodes;
}

static ExceptionData* getParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
return __o__->prv->parent;
}

static void setParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, ExceptionData* p)
{
__o__->prv->parent = p;
}
