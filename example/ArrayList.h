#ifndef FILE_ArrayList_FATHOM
#define FILE_ArrayList_FATHOM

typedef struct ArrayList ArrayList;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <stdlib.h>
#include <Fathom.h>

CLASS
(
	ArrayList, 
	
	, struct Private* prv;
)

ArrayList* fathom_ArrayList_ArrayList(ExceptionData* exceptionData);
void fathom_del_ArrayList(ArrayList** reference, ExceptionData* exceptionData);
void fathom_ArrayList_add(ArrayList* reference, ExceptionData* exceptionData, int fathom_var_90);
void fathom_ArrayList_increaseSize(ArrayList* reference, ExceptionData* exceptionData);
int fathom_ArrayList_getSize(ArrayList* reference, ExceptionData* exceptionData);
int fathom_ArrayList_get(ArrayList* reference, ExceptionData* exceptionData, int fathom_index_99);
#endif