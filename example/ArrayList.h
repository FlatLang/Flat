#ifndef FILE_ArrayList_NOVA
#define FILE_ArrayList_NOVA

typedef struct ArrayList ArrayList;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <stdlib.h>
#include <Fathom.h>

CCLASS_CLASS
(
	ArrayList, 
	
	struct Private* prv;
)


ArrayList* nova_ArrayList_ArrayList(ExceptionData* exceptionData);
void nova_del_ArrayList(ArrayList** this, ExceptionData* exceptionData);
void nova_ArrayList_add(ArrayList* this, ExceptionData* exceptionData, int nova_ArrayList_var_75);
void nova_ArrayList_increaseSize(ArrayList* this, ExceptionData* exceptionData);
int nova_ArrayList_getSize(ArrayList* this, ExceptionData* exceptionData);
int nova_ArrayList_get(ArrayList* this, ExceptionData* exceptionData, int nova_ArrayList_index_94);
#endif