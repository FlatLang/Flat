#ifndef FILE_Object_FATHOM
#define FILE_Object_FATHOM

typedef struct Object Object;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"

CCLASS_CLASS
(
	Object
)


Object* fathom_Object_Object(ExceptionData* exceptionData);
void fathom_del_Object(Object** this, ExceptionData* exceptionData);
String* fathom_Object_toString(Object* this, ExceptionData* exceptionData);
#endif