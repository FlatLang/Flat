#pragma once
#ifndef FILE_Object_NOVA
#define FILE_Object_NOVA

typedef struct Object Object;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
#include "NovaObject.h"
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NativeObject.h>

typedef struct nova_VTable_Object
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Object;

CCLASS_CLASS
(
	Object, 
	
	nova_VTable_Object* vtable;
)

Object* nova_0_Object_construct(Object* this, ExceptionData* exceptionData);
void nova_del_Object(Object** this, ExceptionData* exceptionData);
String* nova_Object_getHashCode(Object* this, ExceptionData* exceptionData);
long_long nova_Object_getHashCodeLong(Object* this, ExceptionData* exceptionData);
String* nova_0_Object_toString(Object* this, ExceptionData* exceptionData);
char nova_0_Object_equals(Object* this, ExceptionData* exceptionData, Object* nova_0_another);
void nova_Object_this(Object* this, ExceptionData* exceptionData);
void nova_Object_super(Object* this, ExceptionData* exceptionData);

#endif