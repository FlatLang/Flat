#pragma once
#ifndef FILE_File_NOVA
#define FILE_File_NOVA

typedef struct File File;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"

CCLASS_CLASS
(
	File, 
	
	struct Private* prv;
)

File* nova_File_File(ExceptionData* exceptionData, String* nova_0_location);
void nova_del_File(File** this, ExceptionData* exceptionData);
void nova_File_reopen(File* this, ExceptionData* exceptionData);
void nova_File_rewind(File* this, ExceptionData* exceptionData);
int nova_File_exists(File* this, ExceptionData* exceptionData);
int nova_File_create(File* this, ExceptionData* exceptionData);
String* nova_File_readAllContents(File* this, ExceptionData* exceptionData);
String* nova_File_readLine(File* this, ExceptionData* exceptionData);
void nova_File_writeLine(File* this, ExceptionData* exceptionData, String* nova_0_line);
void nova_File_write(File* this, ExceptionData* exceptionData, String* nova_0_data);
void nova_File_flush(File* this, ExceptionData* exceptionData);
void nova_File_close(File* this, ExceptionData* exceptionData);

#endif