#ifndef FILE_List_FATHOM
#define FILE_List_FATHOM

typedef struct List List;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "ListNode.h"

CLASS
(
	List, 
	
	FUNC(void, add, List* __o__, ExceptionData* __FATHOM__exception_data, Object* data);
	FUNC(void, remove, List* __o__, ExceptionData* __FATHOM__exception_data, Object* data);
)

List* new_List(ExceptionData* __FATHOM__exception_data);
void del_List(List** __o__, ExceptionData* __FATHOM__exception_data);
#endif