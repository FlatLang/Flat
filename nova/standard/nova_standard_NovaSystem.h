#pragma once
#ifndef FILE_System_NOVA
#define FILE_System_NOVA

typedef struct nova_standard_NovaSystem nova_standard_NovaSystem;

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
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/io/nova_standard_io_NovaStreamReader.h>
#include <nova/standard/io/nova_standard_io_NovaFile.h>
#include <nova/standard/time/nova_standard_time_NovaTime.h>
#include <nova/standard/process/nova_standard_process_NovaProcess.h>
#include <nova/standard/NativeSystem.h>

typedef struct nova_VTable_nova_standard_NovaSystem
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_NovaSystem;

CCLASS_CLASS
(
	nova_standard_NovaSystem, 
	
	nova_VTable_nova_standard_NovaSystem* vtable;
)

void nova_standard_NovaSystemNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaSystem* nova_standard_NovaSystem_Nova0_construct(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_System(nova_standard_NovaSystem** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSystem_static_Nova0_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode);
void nova_standard_NovaSystem_static_Nova1_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode, nova_standard_NovaString* l0_Novamessage);
void nova_standard_NovaSystem_static_Nova2_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode, nova_standard_NovaString* l0_Novamessage, char l0_Novalog);
nova_standard_process_NovaProcess* nova_standard_NovaSystem_static_Novaexecute(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novacommand);
void nova_standard_NovaSystem_Novathis(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSystem_Novasuper(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif