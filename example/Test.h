#ifndef FILE_Test_FATHOM
#define FILE_Test_FATHOM

typedef struct Test Test;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "IO.h"
#include "String.h"
#include "ArrayList.h"
#include "Math.h"
#include "Time.h"
#include "Person.h"
#include "List.h"

CLASS
(
	Test, 
	
	, struct Private* prv;
)

Test* fathom_Test_Test(ExceptionData* exceptionData);
void fathom_del_Test(Test** reference, ExceptionData* exceptionData);
void fathom_Test_main(Test* reference, ExceptionData* exceptionData, String** fathom_args_3);
int fathom_Test_divide(Test* reference, ExceptionData* exceptionData, int fathom_numerator_6, int fathom_denominator_6);
int fathom_Test_getEvenNumber(Test* reference, ExceptionData* exceptionData, int fathom_num_9);
int fathom_Test_test(Test* reference, ExceptionData* exceptionData);
int fathom_Test_test2(Test* reference, ExceptionData* exceptionData);
extern Test* __static__Test;

#endif