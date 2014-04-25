#include "ExceptionData.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <setjmp.h>
#include "ArrayList.h"

PRIVATE
(
jmp_buf* fathom_buffer;
ExceptionData* fathom_parent;
ArrayList* fathom_codes;
)

ExceptionData* fathom_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* fathom_buf_16)
{
NEW(ExceptionData, this);

this->prv->fathom_buffer = 0;
this->prv->fathom_parent = 0;
this->prv->fathom_codes = 0;
{
this->prv->fathom_buffer = fathom_buf_16;
this->prv->fathom_codes = fathom_ArrayList_ArrayList(exceptionData);
}

return this;
}

void fathom_del_ExceptionData(ExceptionData** this, ExceptionData* exceptionData)
{
if (!*this)
{
return;
}


fathom_del_ExceptionData(&(*this)->prv->fathom_parent, exceptionData);
fathom_del_ArrayList(&(*this)->prv->fathom_codes, exceptionData);
free((*this)->prv);

{
}
free(*this);
}

ArrayList* fathom_ExceptionData_getCodes(ExceptionData* this, ExceptionData* exceptionData)
{
return this->prv->fathom_codes;
}

void fathom_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_129)
{
fathom_ArrayList_add(this->prv->fathom_codes, exceptionData, fathom_code_129);
}

jmp_buf* fathom_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData)
{
return this->prv->fathom_buffer;
}

ExceptionData* fathom_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_151)
{
ExceptionData* fathom_data_151;

fathom_data_151 = this;
while (fathom_data_151 != 0)
{
ArrayList* fathom_list_192;
int fathom_i_192;

fathom_list_192 = fathom_ExceptionData_getCodes(fathom_data_151, exceptionData);
for (fathom_i_192 = 0; fathom_i_192 < fathom_ArrayList_getSize(fathom_list_192, exceptionData); fathom_i_192++)
{
if (fathom_ArrayList_get(fathom_list_192, exceptionData, fathom_i_192) == fathom_code_151)
{
return fathom_data_151;
}
}
if (fathom_ExceptionData_getParent(fathom_data_151, exceptionData) == 0)
{
return fathom_data_151;
}
fathom_data_151 = fathom_ExceptionData_getParent(fathom_data_151, exceptionData);
}
return 0;
}

jmp_buf* fathom_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_154)
{
ExceptionData* fathom_data_154;

fathom_data_154 = fathom_ExceptionData_getCorrectData(this, exceptionData, fathom_code_154);
if (fathom_data_154 == 0)
{
return 0;
}
return fathom_ExceptionData_getBuffer(fathom_data_154, exceptionData);
}

void fathom_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_157)
{
ExceptionData* fathom_data_157;
jmp_buf* fathom_buf_157;

fathom_data_157 = fathom_ExceptionData_getCorrectData(this, exceptionData, fathom_code_157);
if (fathom_ExceptionData_getParent(fathom_data_157, exceptionData) == 0)
{
fathom_code_157 = 1;
}
fathom_buf_157 = fathom_ExceptionData_getBuffer(fathom_data_157, exceptionData);
longjmp(*fathom_buf_157, fathom_code_157);
}

ExceptionData* fathom_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData)
{
return this->prv->fathom_parent;
}

void fathom_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* fathom_p_163)
{
this->prv->fathom_parent = fathom_p_163;
}
