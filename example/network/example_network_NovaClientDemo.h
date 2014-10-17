#pragma once
#ifndef FILE_ClientDemo_NOVA
#define FILE_ClientDemo_NOVA

typedef struct example_network_NovaClientDemo example_network_NovaClientDemo;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_NovaConsole.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaNumber.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaByte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaShort.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaInt.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaLong.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaFloat.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaDouble.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaNull.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaArray.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/network/nova_standard_network_NovaClientSocket.h>
#include <example/network/example_network_NovaConnectionThread.h>

typedef struct nova_VTable_example_network_NovaClientDemo
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_example_network_NovaClientDemo;

CCLASS_CLASS
(
	example_network_NovaClientDemo, 
	
	nova_VTable_example_network_NovaClientDemo* vtable;
)

void example_network_NovaClientDemoNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_network_NovaClientDemo* example_network_NovaClientDemo_Nova0_construct(example_network_NovaClientDemo* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_ClientDemo(example_network_NovaClientDemo** this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_network_NovaClientDemo_static_Novamain(example_network_NovaClientDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs);
void example_network_NovaClientDemo_Novathis(example_network_NovaClientDemo* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_network_NovaClientDemo_Novasuper(example_network_NovaClientDemo* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif