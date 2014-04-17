#ifndef FILE_ArrayList_FATHOM
#define FILE_ArrayList_FATHOM

typedef struct ArrayList ArrayList;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <stdio.h>
#include <stdlib.h>
#include <Fathom.h>

CLASS
(
	ArrayList, 
	
	FUNC(void, add, ArrayList* __o__, ExceptionData* __FATHOM__exception_data, int var);
	FUNC(void, increaseSize, ArrayList* __o__, ExceptionData* __FATHOM__exception_data);
	FUNC(int, getSize, ArrayList* __o__, ExceptionData* __FATHOM__exception_data);
	FUNC(int, get, ArrayList* __o__, ExceptionData* __FATHOM__exception_data, int index);
)

ArrayList* new_ArrayList(ExceptionData* __FATHOM__exception_data);
void del_ArrayList(ArrayList** __o__, ExceptionData* __FATHOM__exception_data);
#endif