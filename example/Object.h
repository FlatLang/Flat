#ifndef FILE_Object_NOVA
#define FILE_Object_NOVA

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


Object* nova_Object_Object(ExceptionData* exceptionData);
void nova_del_Object(Object** this, ExceptionData* exceptionData);
String* nova_Object_toString(Object* this, ExceptionData* exceptionData);
#endif