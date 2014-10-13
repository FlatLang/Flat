#pragma once
#ifndef FILE_StreamReader_NOVA
#define FILE_StreamReader_NOVA

typedef struct nova_standard_io_NovaStreamReader nova_standard_io_NovaStreamReader;

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
#include <nova/standard/io/nova_standard_io_NovaInputStream.h>
#include <nova/standard/io/nova_standard_io_NovaFile.h>

typedef struct nova_VTable_nova_standard_io_NovaStreamReader
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	nova_standard_NovaString* (*nova_standard_io_NovaInputStream_Novavirtual0_readString)(nova_standard_io_NovaInputStream*, nova_standard_exception_NovaExceptionData*);
	char* (*nova_standard_io_NovaInputStream_Novavirtual0_readBytes)(nova_standard_io_NovaInputStream*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_nova_standard_io_NovaStreamReader;

CCLASS_CLASS
(
	nova_standard_io_NovaStreamReader, 
	
	nova_VTable_nova_standard_io_NovaStreamReader* vtable;
	struct Private* prv;
)

void nova_standard_io_NovaStreamReaderNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_io_NovaStreamReader* nova_standard_io_NovaStreamReader_Novaconstruct(nova_standard_io_NovaStreamReader* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile);
void nova_del_StreamReader(nova_standard_io_NovaStreamReader** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_io_NovaStreamReader_Novathis(nova_standard_io_NovaStreamReader* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile);
nova_standard_NovaString* nova_standard_io_NovaStreamReader_Novaread(nova_standard_io_NovaStreamReader* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_io_NovaStreamReader_Novasuper(nova_standard_io_NovaStreamReader* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif