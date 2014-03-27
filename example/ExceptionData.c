#include "ExceptionData.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <setjmp.h>
#include "ArrayList.h"

ExceptionData* new_ExceptionData(ExceptionData* __FATHOM__exception_data, jmp_buf* buf);
void del_ExceptionData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static ArrayList* getCodes(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static void addCode(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static jmp_buf* getBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static ExceptionData* getCorrectData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static jmp_buf* getCorrectBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static void jumpToBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
static ExceptionData* getParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
static void setParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, ExceptionData* p);

PRIVATE
(
jmp_buf* buffer;
ExceptionData* parent;
ArrayList* codes;
)

ExceptionData* new_ExceptionData(ExceptionData* __FATHOM__exception_data, jmp_buf* buf)
{
NEW(ExceptionData, __o__);

__o__->getCodes = getCodes;
__o__->addCode = addCode;
__o__->getBuffer = getBuffer;
__o__->getCorrectData = getCorrectData;
__o__->getCorrectBuffer = getCorrectBuffer;
__o__->jumpToBuffer = jumpToBuffer;
__o__->getParent = getParent;
__o__->setParent = setParent;

__o__->prv->buffer = 0;
__o__->prv->parent = 0;
__o__->prv->codes = 0;
__o__->prv->buffer = buf;
__o__->prv->codes = new_ArrayList(__FATHOM__exception_data);

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
del_ArrayList(__o__->prv->codes, __FATHOM__exception_data);
free(__o__->prv);

free(__o__);
}

static ArrayList* getCodes(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
return __o__->prv->codes;
}

static void addCode(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
__o__->prv->codes->add(__o__->prv->codes, __FATHOM__exception_data, code);
}

static jmp_buf* getBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
return __o__->prv->buffer;
}

static ExceptionData* getCorrectData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
ExceptionData* data;
ArrayList* list;
int i;

data = __o__;
while (data != 0)
{
list = data->getCodes(data, __FATHOM__exception_data);
{
for (i = 0; i < list->getSize(list, __FATHOM__exception_data); i = i + 1)
{
if (list->get(list, __FATHOM__exception_data, i) == code)
{
return data;
}
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

static jmp_buf* getCorrectBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
ExceptionData* data;

data = getCorrectData(__o__, __FATHOM__exception_data, code);
if (data == 0)
{
return 0;
}
return data->getBuffer(data, __FATHOM__exception_data);
}

static void jumpToBuffer(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code)
{
ExceptionData* data;
jmp_buf* buf;

data = getCorrectData(__o__, __FATHOM__exception_data, code);
if (data->getParent(data, __FATHOM__exception_data) == 0)
{
code = 1;
}
buf = data->getBuffer(data, __FATHOM__exception_data);
longjmp(*buf, code);
}

static ExceptionData* getParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data)
{
return __o__->prv->parent;
}

static void setParent(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, ExceptionData* p)
{
__o__->prv->parent = p;
}
