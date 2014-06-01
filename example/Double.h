#ifndef FILE_Double_NOVA
#define FILE_Double_NOVA

typedef struct Double Double;

#include <gc.h>
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

CCLASS_CLASS
(
	Double
)


Double* nova_Double_Double(ExceptionData* exceptionData);
void nova_del_Double(Double** this, ExceptionData* exceptionData);
#endif