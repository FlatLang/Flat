#pragma once
#ifndef FILE_System_NOVA
#define FILE_System_NOVA

typedef struct nova_standard_NovaSystem nova_standard_NovaSystem;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/nova_standard_NovaNull.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/nova_standard_NovaConsole.h>
#include <nova/standard/nova_standard_NovaGC.h>
#include <nova/standard/nova_standard_NovaNumber.h>
#include <nova/standard/nova_standard_NovaByte.h>
#include <nova/standard/nova_standard_NovaShort.h>
#include <nova/standard/nova_standard_NovaInt.h>
#include <nova/standard/nova_standard_NovaLong.h>
#include <nova/standard/nova_standard_NovaFloat.h>
#include <nova/standard/nova_standard_NovaDouble.h>
#include <nova/standard/nova_standard_NovaChar.h>
#include <nova/standard/nova_standard_NovaBool.h>
#include <nova/standard/nova_standard_NovaStreamReader.h>
#include <nova/standard/nova_standard_NovaFile.h>
#include <nova/standard/nova_standard_NovaTime.h>
#include <nova/standard/NativeSystem.h>
#include <nova/standard/nova_standard_NovaProcess.h>

typedef struct nova_VTable_nova_standard_NovaSystem
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_NovaSystem;

CCLASS_CLASS
(
	nova_standard_NovaSystem, 
	
	nova_VTable_nova_standard_NovaSystem* vtable;
)

nova_standard_NovaSystem* nova_standard_NovaSystem_Novanull0_construct(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_System(nova_standard_NovaSystem** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSystem_static_Novanull0_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode);
void nova_standard_NovaSystem_static_Novanull1_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode, nova_standard_NovaString* l0_Novamessage);
void nova_standard_NovaSystem_static_Novanull2_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode, nova_standard_NovaString* l0_Novamessage, char l0_Novalog);
nova_standard_NovaProcess* nova_standard_NovaSystem_static_Novaexecute(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novacommand);
void nova_standard_NovaSystem_Novathis(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSystem_Novasuper(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif