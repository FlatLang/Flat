#pragma once
#ifndef FILE_example_Nova_BodyBuilder_NOVA
#define FILE_example_Nova_BodyBuilder_NOVA

typedef struct example_Nova_BodyBuilder example_Nova_BodyBuilder;


#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
#include <nova/standard/exception/nova_standard_exception_Nova_ExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_Nova_Exception.h>
#include <nova/standard/exception/nova_standard_exception_Nova_DivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_Nova_Console.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Number.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Byte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Short.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Int.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Long.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Float.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Double.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Null.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Char.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Bool.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Array.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <example/example_Nova_Person.h>


typedef struct example_Extension_VTable_BodyBuilder example_Extension_VTable_BodyBuilder;
struct example_Extension_VTable_BodyBuilder
{
	nova_Interface_VTable itable;
	long (*nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual1_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_Nova_Object_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
	void (*example_Nova_BodyBuilder_virtual_Nova_sayHello)(example_Nova_BodyBuilder*, nova_standard_exception_Nova_ExceptionData*);
};

extern example_Extension_VTable_BodyBuilder example_Extension_VTable_BodyBuilder_val;


CCLASS_CLASS
(
	example_Nova_BodyBuilder, 
	
	example_Extension_VTable_BodyBuilder* vtable;
	int example_Nova_Person_Nova_age;
	nova_standard_Nova_String* example_Nova_Person_Nova_name;
	int example_Nova_BodyBuilder_Nova_weightClass;
)

void example_Nova_BodyBuilderNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
example_Nova_BodyBuilder* example_Nova_BodyBuilder_1_Nova_construct(example_Nova_BodyBuilder* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_BodyBuilder_Nova_weightClass, nova_standard_Nova_String* example_Nova_BodyBuilder_Nova_name);
void example_Nova_BodyBuilder_Nova_destroy(example_Nova_BodyBuilder** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_Nova_BodyBuilder_1_Nova_this(example_Nova_BodyBuilder* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_BodyBuilder_Nova_weightClass, nova_standard_Nova_String* example_Nova_BodyBuilder_Nova_name);
void example_Nova_BodyBuilder_Nova_sayHello(example_Nova_BodyBuilder* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_Nova_BodyBuilder_0_Nova_super(example_Nova_BodyBuilder* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
