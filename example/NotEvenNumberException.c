#include "NotEvenNumberException.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

NotEvenNumberException* fathom_NotEvenNumberException_NotEvenNumberException(ExceptionData* exceptionData)
{
NEW(NotEvenNumberException, this,);

{
}

return this;
}

void fathom_del_NotEvenNumberException(NotEvenNumberException** this, ExceptionData* exceptionData)
{
if (!*this)
{
return;
}


{
}
free(*this);
}
