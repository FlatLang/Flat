#include "String.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

PRIVATE
(
char* fathom_data;
)

String* fathom_String_String(ExceptionData* exceptionData, char* fathom_data_67)
{
NEW(String, this);

this->prv->fathom_data = 0;
{
this->prv->fathom_data = fathom_data_67;
}

return this;
}

void fathom_del_String(String** this, ExceptionData* exceptionData)
{
if (!*this)
{
return;
}


free((*this)->prv);

{
}
free(*this);
}

char* fathom_String_toCharArray(String* this, ExceptionData* exceptionData)
{
return this->prv->fathom_data;
}

void fathom_String_do1(String* this, ExceptionData* exceptionData)
{
}

void fathom_String_do2(String* this, ExceptionData* exceptionData)
{
}

void fathom_String_do3(String* this, ExceptionData* exceptionData)
{
}

void fathom_String_do4(String* this, ExceptionData* exceptionData)
{
}

void fathom_String_do5(String* this, ExceptionData* exceptionData)
{
}

void fathom_String_do6(String* this, ExceptionData* exceptionData)
{
}
