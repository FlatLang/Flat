#ifndef FILE_ArrayList_FATHOM
#define FILE_ArrayList_FATHOM

typedef struct ArrayList ArrayList;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <stdlib.h>
#include <Fathom.h>

CCLASS_CLASS
(
	ArrayList, 
	
	struct Private* prv;
)


ArrayList* fathom_ArrayList_ArrayList(ExceptionData* exceptionData);
void fathom_del_ArrayList(ArrayList** this, ExceptionData* exceptionData);
void fathom_ArrayList_add(ArrayList* this, ExceptionData* exceptionData, int fathom_var_126);
void fathom_ArrayList_increaseSize(ArrayList* this, ExceptionData* exceptionData);
int fathom_ArrayList_getSize(ArrayList* this, ExceptionData* exceptionData);
int fathom_ArrayList_get(ArrayList* this, ExceptionData* exceptionData, int fathom_index_156);
#endif