#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ClosureStability.h>
typedef struct nova_standard_exception_Nova_ExceptionData nova_standard_exception_Nova_ExceptionData;

typedef int (*stabilitytest_Nova_ClosureStability_closure1_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, int, int);
typedef int (*stabilitytest_Nova_ClosureStability_closure2_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, int, int);
typedef int (*stabilitytest_Nova_ClosureStability_closure3_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, int, int);
typedef double (*stabilitytest_Nova_ClosureStability_closure4_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, double);
typedef double (*stabilitytest_Nova_ClosureStability_closure5_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, double);
typedef double (*stabilitytest_Nova_ClosureStability_closure6_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, double);
typedef void (*stabilitytest_Nova_ClosureStability_closure7_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);
typedef void (*stabilitytest_Nova_ClosureStability_closure8_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);
typedef void (*stabilitytest_Nova_ClosureStability_closure9_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);

stabilitytest_Extension_VTable_ClosureStability stabilitytest_Extension_VTable_ClosureStability_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_ClosureStability_Nova_test,
};


CCLASS_PRIVATE
(
	int stabilitytest_Nova_ClosureStability_Nova_number;
	
)

void stabilitytest_Nova_ClosureStability_Nova_incrementNumber(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ClosureStability_Nova_testClosures(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ClosureStability_Nova_testMathClosures(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ClosureStability_Nova_testInstanceClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
int stabilitytest_Nova_ClosureStability_Nova_callClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ClosureStability_closure3_Nova_closure stabilitytest_Nova_ClosureStability_Nova_closure, void* stabilitytest_Nova_ClosureStability_ref_Nova_closure, int stabilitytest_Nova_ClosureStability_Nova_a, int stabilitytest_Nova_ClosureStability_Nova_b);
double stabilitytest_Nova_ClosureStability_Nova_mathClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ClosureStability_closure6_Nova_closure stabilitytest_Nova_ClosureStability_Nova_closure, void* stabilitytest_Nova_ClosureStability_ref_Nova_closure, double stabilitytest_Nova_ClosureStability_Nova_value);
void stabilitytest_Nova_ClosureStability_Nova_instanceClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ClosureStability_closure9_Nova_closure stabilitytest_Nova_ClosureStability_Nova_closure, void* stabilitytest_Nova_ClosureStability_ref_Nova_closure);
int stabilitytest_Nova_ClosureStability_Nova_multiply(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_ClosureStability_Nova_value1, int stabilitytest_Nova_ClosureStability_Nova_value2);
int stabilitytest_Nova_ClosureStability_Nova_pow(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_ClosureStability_Nova_base, int stabilitytest_Nova_ClosureStability_Nova_pow);
double stabilitytest_Nova_ClosureStability_Nova_TOLERANCE;
void stabilitytest_Nova_ClosureStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ClosureStability* stabilitytest_Nova_ClosureStability_Nova_ClosureStability(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClosureStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_ClosureStability, this);
	this->vtable = &stabilitytest_Extension_VTable_ClosureStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_ClosureStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ClosureStability_Nova_this(this, exceptionData, stabilitytest_Nova_ClosureStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_ClosureStability_Nova_destroy(stabilitytest_Nova_ClosureStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ClosureStability_Nova_this(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClosureStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_ClosureStability_Nova_program);
}

void stabilitytest_Nova_ClosureStability_Nova_incrementNumber(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->stabilitytest_Nova_ClosureStability_Nova_number++;
}

void stabilitytest_Nova_ClosureStability_Nova_test(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	stabilitytest_Nova_ClosureStability_Nova_TOLERANCE = (double)(0.0000000001);
	stabilitytest_Nova_ClosureStability_Nova_testClosures(this, exceptionData);
}

void stabilitytest_Nova_ClosureStability_Nova_testClosures(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Checking closures..."));
	stabilitytest_Nova_ClosureStability_Nova_testMathClosures(this, exceptionData);
	stabilitytest_Nova_ClosureStability_Nova_testInstanceClosure(this, exceptionData);
}

void stabilitytest_Nova_ClosureStability_Nova_testMathClosures(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_a;
	int l1_Nova_b;
	double l1_Nova_value;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Checking static math closures... "));
	l1_Nova_a = (int)(5);
	l1_Nova_b = (int)(3);
	l1_Nova_value = (double)(0.5);
	if (stabilitytest_Nova_ClosureStability_Nova_callClosure(0, exceptionData, (stabilitytest_Nova_ClosureStability_closure3_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_multiply, this, l1_Nova_a, l1_Nova_b) != stabilitytest_Nova_ClosureStability_Nova_multiply(0, exceptionData, l1_Nova_a, l1_Nova_b))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed to call multiply(Int, Int) closure"));
	}
	if (stabilitytest_Nova_ClosureStability_Nova_callClosure(0, exceptionData, (stabilitytest_Nova_ClosureStability_closure3_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_pow, this, l1_Nova_a, l1_Nova_b) != stabilitytest_Nova_ClosureStability_Nova_pow(0, exceptionData, l1_Nova_a, l1_Nova_b))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed to call pow(Int, Int) closure"));
	}
	if (stabilitytest_Nova_ClosureStability_Nova_mathClosure(0, exceptionData, (stabilitytest_Nova_ClosureStability_closure6_Nova_closure)&nova_standard_math_Nova_Math_Nova_sin, 0, l1_Nova_value) - nova_standard_math_Nova_Math_Nova_sin(0, exceptionData, l1_Nova_value) >= stabilitytest_Nova_ClosureStability_Nova_TOLERANCE)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed to call Math.sin(Double) closure"));
	}
	if (stabilitytest_Nova_ClosureStability_Nova_mathClosure(0, exceptionData, (stabilitytest_Nova_ClosureStability_closure6_Nova_closure)&nova_standard_math_Nova_Math_Nova_tan, 0, l1_Nova_value) - nova_standard_math_Nova_Math_Nova_tan(0, exceptionData, l1_Nova_value) >= stabilitytest_Nova_ClosureStability_Nova_TOLERANCE)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed to call Math.tan(Double) closure"));
	}
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "OK"));
}

void stabilitytest_Nova_ClosureStability_Nova_testInstanceClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	stabilitytest_Nova_ClosureStability* l1_Nova_c;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Checking instance closures... "));
	l1_Nova_c = stabilitytest_Nova_ClosureStability_Nova_ClosureStability(0, exceptionData, this->stabilitytest_Nova_StabilityTestCase_Nova_program);
	stabilitytest_Nova_ClosureStability_Nova_instanceClosure(0, exceptionData, (stabilitytest_Nova_ClosureStability_closure9_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_incrementNumber, l1_Nova_c);
	if (l1_Nova_c->prv->stabilitytest_Nova_ClosureStability_Nova_number == 0)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed to call incrementNumber() instance closure"));
	}
	stabilitytest_Nova_ClosureStability_Nova_instanceClosure(0, exceptionData, (stabilitytest_Nova_ClosureStability_closure9_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_incrementNumber, stabilitytest_Nova_ClosureStability_Nova_ClosureStability(0, exceptionData, this->stabilitytest_Nova_StabilityTestCase_Nova_program));
	stabilitytest_Nova_ClosureStability_Nova_instanceClosure(0, exceptionData, (stabilitytest_Nova_ClosureStability_closure9_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_incrementNumber, l1_Nova_c);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "OK"));
}

int stabilitytest_Nova_ClosureStability_Nova_callClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ClosureStability_closure3_Nova_closure stabilitytest_Nova_ClosureStability_Nova_closure, void* stabilitytest_Nova_ClosureStability_ref_Nova_closure, int stabilitytest_Nova_ClosureStability_Nova_a, int stabilitytest_Nova_ClosureStability_Nova_b)
{
	return stabilitytest_Nova_ClosureStability_Nova_closure(stabilitytest_Nova_ClosureStability_ref_Nova_closure, exceptionData, stabilitytest_Nova_ClosureStability_Nova_a, stabilitytest_Nova_ClosureStability_Nova_b);
}

double stabilitytest_Nova_ClosureStability_Nova_mathClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ClosureStability_closure6_Nova_closure stabilitytest_Nova_ClosureStability_Nova_closure, void* stabilitytest_Nova_ClosureStability_ref_Nova_closure, double stabilitytest_Nova_ClosureStability_Nova_value)
{
	return stabilitytest_Nova_ClosureStability_Nova_closure(stabilitytest_Nova_ClosureStability_ref_Nova_closure, exceptionData, stabilitytest_Nova_ClosureStability_Nova_value);
}

void stabilitytest_Nova_ClosureStability_Nova_instanceClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ClosureStability_closure9_Nova_closure stabilitytest_Nova_ClosureStability_Nova_closure, void* stabilitytest_Nova_ClosureStability_ref_Nova_closure)
{
	stabilitytest_Nova_ClosureStability_Nova_closure(stabilitytest_Nova_ClosureStability_ref_Nova_closure, exceptionData);
}

int stabilitytest_Nova_ClosureStability_Nova_multiply(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_ClosureStability_Nova_value1, int stabilitytest_Nova_ClosureStability_Nova_value2)
{
	return stabilitytest_Nova_ClosureStability_Nova_value1 * stabilitytest_Nova_ClosureStability_Nova_value2;
}

int stabilitytest_Nova_ClosureStability_Nova_pow(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_ClosureStability_Nova_base, int stabilitytest_Nova_ClosureStability_Nova_pow)
{
	int l1_Nova_value;
	int l2_Nova_i;
	
	l1_Nova_value = stabilitytest_Nova_ClosureStability_Nova_base;
	l2_Nova_i = (int)(0);
	for (; l2_Nova_i < stabilitytest_Nova_ClosureStability_Nova_pow - 1; l2_Nova_i++)
	{
		l1_Nova_value = l1_Nova_value * stabilitytest_Nova_ClosureStability_Nova_base;
	}
	return l1_Nova_value;
}

void stabilitytest_Nova_ClosureStability_0_Nova_super(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->stabilitytest_Nova_ClosureStability_Nova_number = 0;
}

