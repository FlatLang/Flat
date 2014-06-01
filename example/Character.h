#ifndef FILE_Character_NOVA
#define FILE_Character_NOVA

typedef struct Character Character;

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
	Character
)


Character* nova_Character_Character(ExceptionData* exceptionData);
void nova_del_Character(Character** this, ExceptionData* exceptionData);
#endif