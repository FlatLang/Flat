#ifndef FILE_List_NOVA
#define FILE_List_NOVA

typedef struct List List;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include "ListNode.h"

CCLASS_CLASS
(
	List, 
	
	struct Private* prv;
)


List* nova_List_List(ExceptionData* exceptionData);
void nova_del_List(List** this, ExceptionData* exceptionData);
ListNode* nova_List_getFirst(List* this, ExceptionData* exceptionData);
void nova_List_add(List* this, ExceptionData* exceptionData, Object* nova_List_data_111);
void nova_List_remove(List* this, ExceptionData* exceptionData, Object* nova_List_data_121);
#endif