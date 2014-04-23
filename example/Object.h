#ifndef FILE_Object_FATHOM
#define FILE_Object_FATHOM

typedef struct Object Object;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

CLASS
(
	Object, 
	
	, struct Private* prv;
)

Object* fathom_Object_Object(ExceptionData* exceptionData);
void fathom_del_Object(Object** reference, ExceptionData* exceptionData);
String* fathom_Object_toString(Object* reference, ExceptionData* exceptionData);
#endif