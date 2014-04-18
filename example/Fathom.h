#ifndef FILE_Fathom_FATHOM
#define FILE_Fathom_FATHOM

typedef struct Fathom Fathom;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

CLASS
(
	Fathom, 
	
	ANDROID_DEBUG* false;
	DEBUG* true;
	CSOURCE* 0x1;
	VERBOSE* 0x10;
	DRY_RUN* 0x100;
	KEEP_C* 0x1000;
	C_ARGS* 0x10000;
	RUNTIME* 0x100000;
	GCC* 1;
	TCC* 2;
	CLANG* 3;
	WINDOWS* 1;
	MACOSX* 2;
	LINUX* 3;
	LANGUAGE_NAME* Fathom;
	
)

Fathom* new_Fathom(ExceptionData* __FATHOM__exception_data);
void del_Fathom(Fathom** __o__, ExceptionData* __FATHOM__exception_data);
extern Fathom* __static__Fathom;

#endif