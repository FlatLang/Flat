#pragma once
#ifndef FILE_StreamReader_NOVA
#define FILE_StreamReader_NOVA

typedef struct StreamReader StreamReader;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaInputStream.h>
#include <NovaFile.h>


CCLASS_CLASS
(
	StreamReader, 
	
	struct Private* prv;
)

StreamReader* nova_StreamReader_StreamReader(ExceptionData* exceptionData, File* nova_0_file);
void nova_del_StreamReader(StreamReader** this, ExceptionData* exceptionData);
String* nova_StreamReader_read(StreamReader* this, ExceptionData* exceptionData);

#endif