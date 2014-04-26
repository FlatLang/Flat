#include "Object.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include <windows.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

Object* fathom_Object_Object(ExceptionData* exceptionData)
{
CCLASS_NEW(Object, this,);

{
}

return this;
}

void fathom_del_Object(Object** this, ExceptionData* exceptionData)
{
if (!*this)
{
return;
}


{
}
free(*this);
}

String* fathom_Object_toString(Object* this, ExceptionData* exceptionData)
{
}
