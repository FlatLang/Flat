#pragma once
#ifndef FILE_ArrayListDemo_NOVA
#define FILE_ArrayListDemo_NOVA

typedef struct ArrayListDemo ArrayListDemo;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
#include <NovaObject.h>
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
#include <NovaArrayList.h>
#include <NovaAnimal.h>
#include <NovaSpider.h>
#include <NovaDog.h>

typedef struct nova_VTable_ArrayListDemo
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_ArrayListDemo;

CCLASS_CLASS
(
	ArrayListDemo, 
	
	nova_VTable_ArrayListDemo* vtable;
)

ArrayListDemo* nova_0_ArrayListDemo_construct(ArrayListDemo* this, ExceptionData* exceptionData);
void nova_del_ArrayListDemo(ArrayListDemo** this, ExceptionData* exceptionData);
void nova_static_ArrayListDemo_main(ArrayListDemo* this, ExceptionData* exceptionData, String** nova_0_args);
void nova_ArrayListDemo_this(ArrayListDemo* this, ExceptionData* exceptionData);
void nova_ArrayListDemo_super(ArrayListDemo* this, ExceptionData* exceptionData);

#endif