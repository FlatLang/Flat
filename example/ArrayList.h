#ifndef FILE_ArrayList_FATHOM
#define FILE_ArrayList_FATHOM

typedef struct ArrayList ArrayList;

#include <CClass.h>
#include <ExceptionHandler.h>
#include <windows.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
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
void fathom_ArrayList_add(ArrayList* this, ExceptionData* exceptionData, int fathom_var_34);
void fathom_ArrayList_increaseSize(ArrayList* this, ExceptionData* exceptionData);
int fathom_ArrayList_getSize(ArrayList* this, ExceptionData* exceptionData);
int fathom_ArrayList_get(ArrayList* this, ExceptionData* exceptionData, int fathom_index_51);
#endif