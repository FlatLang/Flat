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
	
	FUNC(String*, toString, Object* __o__, ExceptionData* __FATHOM__exception_data);
)

Object* new_Object(ExceptionData* __FATHOM__exception_data);
void del_Object(Object** __o__, ExceptionData* __FATHOM__exception_data);
#endif