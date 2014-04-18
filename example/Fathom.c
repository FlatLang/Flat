#include "Fathom.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

Fathom* __static__Fathom;

Fathom* new_Fathom(ExceptionData* __FATHOM__exception_data);
void del_Fathom(Fathom** __o__, ExceptionData* __FATHOM__exception_data);

PRIVATE
(
	int compiler;
	long_long flags;
	startTime* endTime;
	outputFile* workingDir;
	SyntaxTree* tree;
	static const int os;
	static const String* OUTPUT_EXTENSION;
)

Fathom* new_Fathom(ExceptionData* __FATHOM__exception_data)
{
	NEW(Fathom, __o__);
	
	
	__o__->false = 0;
	__o__->true = 0;
	__o__->0x1 = 0;
	__o__->0x10 = 0;
	__o__->0x100 = 0;
	__o__->0x1000 = 0;
	__o__->0x10000 = 0;
	__o__->0x100000 = 0;
	__o__->1 = 0;
	__o__->2 = 0;
	__o__->3 = 0;
	__o__->1 = 0;
	__o__->2 = 0;
	__o__->3 = 0;
	__o__->Fathom = 0;
	__o__->prv->compiler = 0;
	__o__->prv->flags = 0;
	__o__->prv->endTime = 0;
	__o__->prv->workingDir = 0;
	__o__->prv->tree = 0;
	__o__->prv->os = 0;
	__o__->prv->OUTPUT_EXTENSION = 0;
	{
	}
	
	return __o__;
}

void del_Fathom(Fathom** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	del_startTime(&(*__o__)->prv->endTime, __FATHOM__exception_data);
	del_outputFile(&(*__o__)->prv->workingDir, __FATHOM__exception_data);
	del_SyntaxTree(&(*__o__)->prv->tree, __FATHOM__exception_data);
	del_String(&(*__o__)->prv->OUTPUT_EXTENSION, __FATHOM__exception_data);
	free((*__o__)->prv);
	del_ANDROID_DEBUG(&(*__o__)->false, __FATHOM__exception_data);
	del_DEBUG(&(*__o__)->true, __FATHOM__exception_data);
	del_CSOURCE(&(*__o__)->0x1, __FATHOM__exception_data);
	del_VERBOSE(&(*__o__)->0x10, __FATHOM__exception_data);
	del_DRY_RUN(&(*__o__)->0x100, __FATHOM__exception_data);
	del_KEEP_C(&(*__o__)->0x1000, __FATHOM__exception_data);
	del_C_ARGS(&(*__o__)->0x10000, __FATHOM__exception_data);
	del_RUNTIME(&(*__o__)->0x100000, __FATHOM__exception_data);
	del_GCC(&(*__o__)->1, __FATHOM__exception_data);
	del_TCC(&(*__o__)->2, __FATHOM__exception_data);
	del_CLANG(&(*__o__)->3, __FATHOM__exception_data);
	del_WINDOWS(&(*__o__)->1, __FATHOM__exception_data);
	del_MACOSX(&(*__o__)->2, __FATHOM__exception_data);
	del_LINUX(&(*__o__)->3, __FATHOM__exception_data);
	del_LANGUAGE_NAME(&(*__o__)->Fathom, __FATHOM__exception_data);
	
	{
	}
	free(*__o__);
}
