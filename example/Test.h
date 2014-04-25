#ifndef FILE_Test_FATHOM
#define FILE_Test_FATHOM

typedef struct Test Test;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include "IO.h"
#include "String.h"
#include "ArrayList.h"
#include "Math.h"
#include "Time.h"
#include "Person.h"
#include "List.h"
#include "NotEvenNumberException.h"

CLASS
(
Test
)

Test* fathom_Test_Test(ExceptionData* exceptionData);
void fathom_del_Test(Test** this, ExceptionData* exceptionData);
void fathom_Test_main(ExceptionData* exceptionData, String** fathom_args_85);
int fathom_Test_divide(Test* this, ExceptionData* exceptionData, int fathom_numerator_96, int fathom_denominator_96);
int fathom_Test_getEvenNumber(Test* this, ExceptionData* exceptionData, int fathom_num_108);
int fathom_Test_test(Test* this, ExceptionData* exceptionData);
int fathom_Test_test2(Test* this, ExceptionData* exceptionData);
extern Test* __static__Test;

#endif