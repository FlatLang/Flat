#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ClosureStability.h>

typedef int (*l0_1_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, int, int);
typedef int (*l0_2_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, int, int);
typedef int (*l0_3_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, int, int);
typedef double (*l0_4_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, double);
typedef double (*l0_5_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, double);
typedef double (*l0_6_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*, double);
typedef void (*l0_7_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);
typedef void (*l0_8_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);
typedef void (*l0_9_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);

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
	},
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	int stabilitytest_Nova_ClosureStability_Nova_number;
	
)

void stabilitytest_Nova_ClosureStability_Nova_incrementNumber(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ClosureStability_Nova_testClosures(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
void stabilitytest_Nova_ClosureStability_Nova_testMathClosures(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
void stabilitytest_Nova_ClosureStability_Nova_testInstanceClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
int stabilitytest_Nova_ClosureStability_Nova_callClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_3_Nova_closure l0_Nova_closure, void* l0_ref_Nova_closure, int l0_Nova_a, int l0_Nova_b);
double stabilitytest_Nova_ClosureStability_Nova_mathClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_6_Nova_closure l0_Nova_closure, void* l0_ref_Nova_closure, double l0_Nova_value);
void stabilitytest_Nova_ClosureStability_Nova_instanceClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_9_Nova_closure l0_Nova_closure, void* l0_ref_Nova_closure);
int stabilitytest_Nova_ClosureStability_Nova_multiply(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_value1, int l0_Nova_value2);
int stabilitytest_Nova_ClosureStability_Nova_pow(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_base, int l0_Nova_pow);
double stabilitytest_Nova_ClosureStability_Nova_TOLERANCE;
void stabilitytest_Nova_ClosureStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ClosureStability* stabilitytest_Nova_ClosureStability_2_Nova_construct(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_Nova_ClosureStability, this);
	this->vtable = &stabilitytest_Extension_VTable_ClosureStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	stabilitytest_Nova_ClosureStability_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ClosureStability_2_Nova_this(this, exceptionData);
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

void stabilitytest_Nova_ClosureStability_Nova_incrementNumber(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->stabilitytest_Nova_ClosureStability_Nova_number++;
}

void stabilitytest_Nova_ClosureStability_Nova_test(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	stabilitytest_Nova_ClosureStability_Nova_TOLERANCE = 0.0000000001;
	stabilitytest_Nova_ClosureStability_Nova_testClosures((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, l0_Nova_program);
}

void stabilitytest_Nova_ClosureStability_Nova_testClosures(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Testing closures..."));
	stabilitytest_Nova_ClosureStability_Nova_testMathClosures((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, l0_Nova_program);
	stabilitytest_Nova_ClosureStability_Nova_testInstanceClosure((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, l0_Nova_program);
}

void stabilitytest_Nova_ClosureStability_Nova_testMathClosures(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	int l1_Nova_a;
	int l1_Nova_b;
	double l1_Nova_value;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Testing static math closures... "));
	l1_Nova_a = 5;
	l1_Nova_b = 3;
	l1_Nova_value = 0.5;
	if (stabilitytest_Nova_ClosureStability_Nova_callClosure((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, (l0_3_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_multiply, (stabilitytest_Nova_ClosureStability*)nova_null, l1_Nova_a, l1_Nova_b) != stabilitytest_Nova_ClosureStability_Nova_multiply((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, l1_Nova_a, l1_Nova_b))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to call multiply(Int, Int) closure"));
	}
	if (stabilitytest_Nova_ClosureStability_Nova_callClosure((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, (l0_3_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_pow, (stabilitytest_Nova_ClosureStability*)nova_null, l1_Nova_a, l1_Nova_b) != stabilitytest_Nova_ClosureStability_Nova_pow((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, l1_Nova_a, l1_Nova_b))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to call pow(Int, Int) closure"));
	}
	if (stabilitytest_Nova_ClosureStability_Nova_mathClosure((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, (l0_6_Nova_closure)&nova_standard_math_Nova_Math_Nova_sin, 0, l1_Nova_value) - nova_standard_math_Nova_Math_Nova_sin(0, exceptionData, l1_Nova_value) >= stabilitytest_Nova_ClosureStability_Nova_TOLERANCE)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to call Math.sin(Double) closure"));
	}
	if (stabilitytest_Nova_ClosureStability_Nova_mathClosure((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, (l0_6_Nova_closure)&nova_standard_math_Nova_Math_Nova_tan, 0, l1_Nova_value) - nova_standard_math_Nova_Math_Nova_tan(0, exceptionData, l1_Nova_value) >= stabilitytest_Nova_ClosureStability_Nova_TOLERANCE)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to call Math.tan(Double) closure"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_ClosureStability_Nova_testInstanceClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	stabilitytest_Nova_ClosureStability* l1_Nova_c;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Testing instance closures... "));
	l1_Nova_c = stabilitytest_Nova_ClosureStability_2_Nova_construct(0, exceptionData);
	stabilitytest_Nova_ClosureStability_Nova_instanceClosure((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, (l0_9_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_incrementNumber, l1_Nova_c);
	if (l1_Nova_c->prv->stabilitytest_Nova_ClosureStability_Nova_number == 0)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to call incrementNumber() instance closure"));
	}
	stabilitytest_Nova_ClosureStability_Nova_instanceClosure((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, (l0_9_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_incrementNumber, stabilitytest_Nova_ClosureStability_2_Nova_construct(0, exceptionData));
	stabilitytest_Nova_ClosureStability_Nova_instanceClosure((stabilitytest_Nova_ClosureStability*)nova_null, exceptionData, (l0_9_Nova_closure)&stabilitytest_Nova_ClosureStability_Nova_incrementNumber, l1_Nova_c);
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

int stabilitytest_Nova_ClosureStability_Nova_callClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_3_Nova_closure l0_Nova_closure, void* l0_ref_Nova_closure, int l0_Nova_a, int l0_Nova_b)
{
	return l0_Nova_closure(l0_ref_Nova_closure, exceptionData, l0_Nova_a, l0_Nova_b);
}

double stabilitytest_Nova_ClosureStability_Nova_mathClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_6_Nova_closure l0_Nova_closure, void* l0_ref_Nova_closure, double l0_Nova_value)
{
	return l0_Nova_closure(l0_ref_Nova_closure, exceptionData, l0_Nova_value);
}

void stabilitytest_Nova_ClosureStability_Nova_instanceClosure(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_9_Nova_closure l0_Nova_closure, void* l0_ref_Nova_closure)
{
	l0_Nova_closure(l0_ref_Nova_closure, exceptionData);
}

int stabilitytest_Nova_ClosureStability_Nova_multiply(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_value1, int l0_Nova_value2)
{
	return l0_Nova_value1 * l0_Nova_value2;
}

int stabilitytest_Nova_ClosureStability_Nova_pow(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_base, int l0_Nova_pow)
{
	int l1_Nova_value;
	int l1_Nova_i;
	
	l1_Nova_value = l0_Nova_base;
	l1_Nova_i = 0;
	for (; l1_Nova_i < l0_Nova_pow - 1; l1_Nova_i++)
	{
		l1_Nova_value = l1_Nova_value * l0_Nova_base;
	}
	return l1_Nova_value;
}

void stabilitytest_Nova_ClosureStability_2_Nova_this(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void stabilitytest_Nova_ClosureStability_Nova_super(stabilitytest_Nova_ClosureStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->stabilitytest_Nova_ClosureStability_Nova_number = 0;
}

