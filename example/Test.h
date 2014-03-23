#ifndef FILE_Test_FATHOM
#define FILE_Test_FATHOM

#include "CClass.h"
#include "ExceptionHandler.h"
#include "IO.h"
#include "String.h"

CLASS
(
Test, 

String* publicVariable;

FUNC(void*, __FATHOM__main, Test* __o__, String** args);
FUNC(int, divide, Test* __o__, int numerator, int denominator);
FUNC(int, test, Test* __o__);
FUNC(int, test2, Test* __o__);
)

Test* new_Test();
void del_Test(Test* __o__);
extern Test* __static__Test;

#endif