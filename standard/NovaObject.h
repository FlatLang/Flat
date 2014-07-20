#pragma once
#ifndef FILE_Object_NOVA
#define FILE_Object_NOVA

typedef struct Object Object;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include "NovaObject.h"
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NativeObject.h>

typedef struct nova_VTable_Object
{
	String* (*nova_virtual_toString)(Object*, ExceptionData*);
	char (*nova_virtual_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Object;

CCLASS_CLASS
(
	Object, 
	
	nova_VTable_Object* vtable;
)

Object* nova_Object_Object(ExceptionData* exceptionData);
void nova_del_Object(Object** this, ExceptionData* exceptionData);
String* nova_Object_toString(Object* this, ExceptionData* exceptionData);
char nova_Object_equals(Object* this, ExceptionData* exceptionData, Object* nova_0_another);

#endif