#pragma once
#ifndef FILE_File_NOVA
#define FILE_File_NOVA

typedef struct nova_standard_NovaFile nova_standard_NovaFile;

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
#include <nova/standard/NativeFile.h>
#include <nova/standard/nova_standard_NovaTime.h>

typedef struct nova_VTable_nova_standard_NovaFile
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_NovaFile;

CCLASS_CLASS
(
	nova_standard_NovaFile, 
	
	nova_VTable_nova_standard_NovaFile* vtable;
	struct Private* prv;
)

nova_standard_NovaFile* nova_standard_NovaFile_Novanull1_construct(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novalocation);
nova_standard_NovaFile* nova_standard_NovaFile_Novanull2_construct(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, FILE* l0_Novafp);
void nova_del_File(nova_standard_NovaFile** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaFile_Novanull0_this(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novalocation);
void nova_standard_NovaFile_Novanull1_this(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, FILE* l0_Novafp);
char nova_standard_NovaFile_Novadelete(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaFile_Novareopen(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaFile_Novarewind(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
char nova_standard_NovaFile_Novaexists(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaFile_NovaclearContents(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
char nova_standard_NovaFile_Novacreate(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaFile_NovareadAllContents(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaFile_NovareadLine(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaFile_NovawriteLine(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaline);
void nova_standard_NovaFile_Novawrite(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadata);
void nova_standard_NovaFile_Novaflush(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaFile_Novaclose(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
int nova_standard_NovaFile_static_NovagetMaxOpenFiles(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaFile_static_NovasetMaxOpenFiles(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanum);
void nova_standard_NovaFile_Novasuper(nova_standard_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif