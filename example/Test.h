#ifndef FILE_Test_FATHOM
#define FILE_Test_FATHOM

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "IO.h"
#include "String.h"
#include "ArrayList.h"
#include "Math.h"
#include "Time.h"

typedef struct ExceptionData ExceptionData;
typedef struct IO IO;
typedef struct String String;
typedef struct ArrayList ArrayList;
typedef struct Math Math;
typedef struct Time Time;

CLASS
(
Test, 

String* publicVariable;

FUNC(void, main, Test* __o__, ExceptionData* __FATHOM__exception_data, String** args);
FUNC(int, divide, Test* __o__, ExceptionData* __FATHOM__exception_data, int numerator, int denominator);
FUNC(int, getEvenNumber, Test* __o__, ExceptionData* __FATHOM__exception_data, int num);
FUNC(int, test, Test* __o__, ExceptionData* __FATHOM__exception_data);
FUNC(int, test2, Test* __o__, ExceptionData* __FATHOM__exception_data);
)

Test* new_Test(ExceptionData* __FATHOM__exception_data);
void del_Test(Test* __o__, ExceptionData* __FATHOM__exception_data);
extern Test* __static__Test;

#endif