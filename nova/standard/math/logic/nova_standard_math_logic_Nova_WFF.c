#include <precompiled.h>
#include <nova/standard/math/logic/nova_standard_math_logic_Nova_WFF.h>


nova_standard_math_logic_VTable_WFF nova_standard_math_logic_VTable_WFF_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
CCLASS_PRIVATE
(
	nova_standard_math_logic_Nova_StatementLetter** nova_standard_math_logic_Nova_WFF_Nova_letters;
	nova_standard_datastruct_Nova_ArrayList* nova_standard_math_logic_Nova_WFF_Nova_hypotheses;
	nova_standard_math_logic_Nova_Conclusion* nova_standard_math_logic_Nova_WFF_Nova_conclusion;
	nova_standard_Nova_String* nova_standard_math_logic_Nova_WFF_Nova_wff;
	
)

void nova_standard_math_logic_Nova_WFF_Nova_decodeFormula(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_math_logic_Nova_StatementGroup* nova_standard_math_logic_Nova_WFF_Nova_searchForConclusion(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff);
nova_standard_datastruct_Nova_ArrayList* nova_standard_math_logic_Nova_WFF_Nova_decodeHypotheses(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff);
nova_standard_math_logic_Nova_StatementGroup* nova_standard_math_logic_Nova_WFF_Nova_generateHypothesis(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, int l0_Nova_start, int l0_Nova_end);
nova_standard_math_logic_Nova_StatementGroup* nova_standard_math_logic_Nova_WFF_Nova_searchForStatement(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff);
int nova_standard_math_logic_Nova_WFF_Nova_nextNonWhitespaceIndex(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, int l0_Nova_index, int l0_Nova_direction);
int nova_standard_math_logic_Nova_WFF_0_Nova_nextWhitespaceIndex(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, int l0_Nova_index, int l0_Nova_direction);
int nova_standard_math_logic_Nova_WFF_1_Nova_nextWhitespaceIndex(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, int l0_Nova_index, int l0_Nova_direction, char l0_Nova_opposite);
char nova_standard_math_logic_Nova_WFF_Nova_containsChar(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_needle, char* l0_Nova_Chars, int l0_Nova_length);
int nova_standard_math_logic_Nova_WFF_Nova_findEndingMatch(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, char l0_Nova_start, char l0_Nova_end, int l0_Nova_index, int l0_Nova_direction);
char* nova_standard_math_logic_Nova_WFF_Nova_whitespace;
void nova_standard_math_logic_Nova_WFFNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_standard_math_logic_Nova_WFF_Nova_whitespace = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 4);
		nova_standard_math_logic_Nova_WFF_Nova_whitespace[0] = ' ';
		nova_standard_math_logic_Nova_WFF_Nova_whitespace[1] = '\t';
		nova_standard_math_logic_Nova_WFF_Nova_whitespace[2] = '\n';
		nova_standard_math_logic_Nova_WFF_Nova_whitespace[3] = '\r';
	}
}

nova_standard_math_logic_Nova_WFF* nova_standard_math_logic_Nova_WFF_Nova_construct(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wellFormedFormula, nova_standard_math_logic_Nova_StatementLetter** l0_Nova_letters)
{
	CCLASS_NEW(nova_standard_math_logic_Nova_WFF, this);
	this->vtable = &nova_standard_math_logic_VTable_WFF_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_math_logic_Nova_WFF_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_logic_Nova_WFF_Nova_this(this, exceptionData, l0_Nova_wellFormedFormula, l0_Nova_letters);
	}
	
	return this;
}

void nova_standard_math_logic_Nova_WFF_Nova_destroy(nova_standard_math_logic_Nova_WFF** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv->nova_standard_math_logic_Nova_WFF_Nova_letters);
	nova_standard_datastruct_Nova_ArrayList_Nova_destroy(&(*this)->prv->nova_standard_math_logic_Nova_WFF_Nova_hypotheses, exceptionData);
	nova_standard_math_logic_Nova_Conclusion_Nova_destroy(&(*this)->prv->nova_standard_math_logic_Nova_WFF_Nova_conclusion, exceptionData);
	nova_standard_Nova_String_Nova_destroy(&(*this)->prv->nova_standard_math_logic_Nova_WFF_Nova_wff, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_math_logic_Nova_WFF_Nova_this(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wellFormedFormula, nova_standard_math_logic_Nova_StatementLetter** l0_Nova_letters)
{
	this->prv->nova_standard_math_logic_Nova_WFF_Nova_wff = nova_standard_Nova_String_Nova_trim(l0_Nova_wellFormedFormula, exceptionData);
	this->prv->nova_standard_math_logic_Nova_WFF_Nova_letters = l0_Nova_letters;
	this->prv->nova_standard_math_logic_Nova_WFF_Nova_hypotheses = nova_standard_datastruct_Nova_ArrayList_2_Nova_construct(0, exceptionData);
	nova_standard_math_logic_Nova_WFF_Nova_decodeFormula(this, exceptionData);
}

void nova_standard_math_logic_Nova_WFF_Nova_decodeFormula(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_math_logic_Nova_StatementGroup* l1_Nova_conclusionGroup;
	nova_standard_math_logic_Nova_Conclusion* l1_Nova_conclusion;
	nova_standard_datastruct_Nova_ArrayList* l1_Nova_statements;
	int l3_Nova_i;
	int l4_Nova_n;
	
	l1_Nova_conclusionGroup = nova_standard_math_logic_Nova_WFF_Nova_searchForConclusion(this, exceptionData, this->prv->nova_standard_math_logic_Nova_WFF_Nova_wff);
	if (l1_Nova_conclusionGroup == (nova_standard_math_logic_Nova_StatementGroup*)nova_null)
	{
		THROW(3, nova_standard_math_logic_Nova_InvalidFormulaException_1_Nova_construct(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "No valid conclusion found")));
	}
	l1_Nova_conclusion = (nova_standard_math_logic_Nova_Conclusion*)(l1_Nova_conclusionGroup->nova_standard_math_logic_Nova_StatementGroup_Nova_statement);
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Conclusion: "), exceptionData, l1_Nova_conclusion->vtable->nova_standard_math_logic_Nova_LogicalStatement_virtual0_Nova_toString((nova_standard_math_logic_Nova_LogicalStatement*)(l1_Nova_conclusion), exceptionData)));
	this->prv->nova_standard_math_logic_Nova_WFF_Nova_wff = nova_standard_datastruct_Nova_Bounds_Nova_extractPreString(l1_Nova_conclusionGroup->nova_standard_math_logic_Nova_StatementGroup_Nova_bounds, exceptionData, this->prv->nova_standard_math_logic_Nova_WFF_Nova_wff);
	l1_Nova_statements = nova_standard_math_logic_Nova_WFF_Nova_decodeHypotheses(this, exceptionData, this->prv->nova_standard_math_logic_Nova_WFF_Nova_wff);
	l3_Nova_i = 0;
	for (; l3_Nova_i < l1_Nova_statements->nova_standard_datastruct_Nova_ArrayList_Nova_size; l3_Nova_i++)
	{
		nova_standard_math_logic_Nova_StatementGroup* l3_Nova_group;
		
		l3_Nova_group = (nova_standard_math_logic_Nova_StatementGroup*)(((nova_standard_math_logic_Nova_StatementGroup*)nova_standard_datastruct_Nova_ArrayList_Nova_get(l1_Nova_statements, exceptionData, l3_Nova_i)));
		nova_standard_datastruct_Nova_ArrayList_0_Nova_add(this->prv->nova_standard_math_logic_Nova_WFF_Nova_hypotheses, exceptionData, l3_Nova_group->nova_standard_math_logic_Nova_StatementGroup_Nova_statement);
	}
	l4_Nova_n = 0;
	for (; l4_Nova_n < this->prv->nova_standard_math_logic_Nova_WFF_Nova_hypotheses->nova_standard_datastruct_Nova_ArrayList_Nova_size; l4_Nova_n++)
	{
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, ((nova_standard_math_logic_Nova_Hypothesis*)nova_standard_datastruct_Nova_ArrayList_Nova_get(this->prv->nova_standard_math_logic_Nova_WFF_Nova_hypotheses, exceptionData, l4_Nova_n)));
	}
}

nova_standard_math_logic_Nova_StatementGroup* nova_standard_math_logic_Nova_WFF_Nova_searchForConclusion(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff)
{
	int l1_Nova_index;
	int l1_Nova_conclusionStart;
	nova_standard_datastruct_Nova_Bounds* l1_Nova_bounds;
	nova_standard_math_logic_Nova_Conclusion* l1_Nova_conclusion;
	nova_standard_math_logic_Nova_StatementGroup* l1_Nova_group;
	
	l1_Nova_index = nova_standard_Nova_String_Nova_lastIndexOf(l0_Nova_wff, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "->"));
	if (l1_Nova_index < 0)
	{
		return (nova_standard_math_logic_Nova_StatementGroup*)nova_null;
	}
	if (nova_standard_Nova_String_Nova_lastChar(l0_Nova_wff, exceptionData) == ')')
{
	int l2_Nova_first;
	
	l2_Nova_first = nova_standard_math_logic_Nova_WFF_Nova_findEndingMatch(this, exceptionData, l0_Nova_wff, '(', ')', l0_Nova_wff->nova_standard_Nova_String_Nova_length - 1, -1);
}
l1_Nova_conclusionStart = nova_standard_math_logic_Nova_WFF_Nova_nextNonWhitespaceIndex(this, exceptionData, l0_Nova_wff, l1_Nova_index + 2, 1);
l1_Nova_bounds = nova_standard_datastruct_Nova_Bounds_3_Nova_construct(0, exceptionData, l1_Nova_conclusionStart, l0_Nova_wff->nova_standard_Nova_String_Nova_length);
l1_Nova_conclusion = nova_standard_math_logic_Nova_Conclusion_Nova_construct(0, exceptionData, nova_standard_datastruct_Nova_Bounds_Nova_extractString(l1_Nova_bounds, exceptionData, l0_Nova_wff));
l1_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_start = nova_standard_math_logic_Nova_WFF_Nova_nextNonWhitespaceIndex(this, exceptionData, l0_Nova_wff, l1_Nova_index - 1, -1) + 1;
l1_Nova_group = nova_standard_math_logic_Nova_StatementGroup_Nova_construct(0, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_conclusion), l1_Nova_bounds);
return l1_Nova_group;}

nova_standard_datastruct_Nova_ArrayList* nova_standard_math_logic_Nova_WFF_Nova_decodeHypotheses(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff)
{
	nova_standard_datastruct_Nova_ArrayList* l1_Nova_list;
	int l1_Nova_next;
	int l1_Nova_prev;
	int l1_Nova_index;
	
	l1_Nova_list = nova_standard_datastruct_Nova_ArrayList_2_Nova_construct(0, exceptionData);
	l1_Nova_next = 0;
	l1_Nova_prev = 0;
	l1_Nova_index = nova_standard_Nova_String_1_Nova_indexOf(l0_Nova_wff, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "&"));
	for (; l1_Nova_index >= 0; l1_Nova_index = nova_standard_Nova_String_2_Nova_indexOf(l0_Nova_wff, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "&"), l1_Nova_prev + 1))
	{
		l1_Nova_next = nova_standard_math_logic_Nova_WFF_Nova_nextNonWhitespaceIndex(this, exceptionData, l0_Nova_wff, l1_Nova_index - 1, -1);
		nova_standard_datastruct_Nova_ArrayList_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(nova_standard_math_logic_Nova_WFF_Nova_generateHypothesis(this, exceptionData, l0_Nova_wff, l1_Nova_prev, l1_Nova_next + 1)));
		l1_Nova_prev = nova_standard_math_logic_Nova_WFF_Nova_nextNonWhitespaceIndex(this, exceptionData, l0_Nova_wff, l1_Nova_index + 1, 1);
	}
	nova_standard_datastruct_Nova_ArrayList_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(nova_standard_math_logic_Nova_WFF_Nova_generateHypothesis(this, exceptionData, l0_Nova_wff, l1_Nova_prev, l0_Nova_wff->nova_standard_Nova_String_Nova_length)));
	return l1_Nova_list;
}

nova_standard_math_logic_Nova_StatementGroup* nova_standard_math_logic_Nova_WFF_Nova_generateHypothesis(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, int l0_Nova_start, int l0_Nova_end)
{
	nova_standard_datastruct_Nova_Bounds* l1_Nova_bounds;
	nova_standard_math_logic_Nova_Hypothesis* l1_Nova_h;
	nova_standard_math_logic_Nova_StatementGroup* l1_Nova_group;
	
	l1_Nova_bounds = nova_standard_datastruct_Nova_Bounds_3_Nova_construct(0, exceptionData, l0_Nova_start, l0_Nova_end);
	l1_Nova_h = nova_standard_math_logic_Nova_Hypothesis_0_Nova_construct(0, exceptionData, nova_standard_datastruct_Nova_Bounds_Nova_extractString(l1_Nova_bounds, exceptionData, l0_Nova_wff));
	l1_Nova_group = nova_standard_math_logic_Nova_StatementGroup_Nova_construct(0, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_h), l1_Nova_bounds);
	return l1_Nova_group;
}

nova_standard_math_logic_Nova_StatementGroup* nova_standard_math_logic_Nova_WFF_Nova_searchForStatement(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff)
{
	return (nova_standard_math_logic_Nova_StatementGroup*)nova_null;
}

int nova_standard_math_logic_Nova_WFF_Nova_nextNonWhitespaceIndex(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, int l0_Nova_index, int l0_Nova_direction)
{
	return nova_standard_math_logic_Nova_WFF_1_Nova_nextWhitespaceIndex((nova_standard_math_logic_Nova_WFF*)nova_null, exceptionData, l0_Nova_wff, l0_Nova_index, l0_Nova_direction, 1);
}

int nova_standard_math_logic_Nova_WFF_0_Nova_nextWhitespaceIndex(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, int l0_Nova_index, int l0_Nova_direction)
{
	return nova_standard_math_logic_Nova_WFF_1_Nova_nextWhitespaceIndex((nova_standard_math_logic_Nova_WFF*)nova_null, exceptionData, l0_Nova_wff, l0_Nova_index, l0_Nova_direction, 0);
}

int nova_standard_math_logic_Nova_WFF_1_Nova_nextWhitespaceIndex(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, int l0_Nova_index, int l0_Nova_direction, char l0_Nova_opposite)
{
	int l1_Nova_i;
	
	l1_Nova_i = l0_Nova_index;
	for (; l1_Nova_i < l0_Nova_wff->nova_standard_Nova_String_Nova_length && l1_Nova_i >= 0; l1_Nova_i = l1_Nova_i + l0_Nova_direction)
	{
		char l1_Nova_c;
		
		l1_Nova_c = nova_standard_Nova_String_Nova_charAt(l0_Nova_wff, exceptionData, l1_Nova_i);
		if (nova_standard_math_logic_Nova_WFF_Nova_containsChar((nova_standard_math_logic_Nova_WFF*)nova_null, exceptionData, l1_Nova_c, nova_standard_math_logic_Nova_WFF_Nova_whitespace, 4) != l0_Nova_opposite)
		{
			return l1_Nova_i;
		}
	}
	return -1;
}

char nova_standard_math_logic_Nova_WFF_Nova_containsChar(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_needle, char* l0_Nova_Chars, int l0_Nova_length)
{
	int l1_Nova_i;
	
	l1_Nova_i = 0;
	for (; l1_Nova_i < l0_Nova_length; l1_Nova_i++)
	{
		if (l0_Nova_needle == l0_Nova_Chars[l1_Nova_i])
		{
			return 1;
		}
	}
	return 0;
}

int nova_standard_math_logic_Nova_WFF_Nova_findEndingMatch(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_wff, char l0_Nova_start, char l0_Nova_end, int l0_Nova_index, int l0_Nova_direction)
{
	int l1_Nova_scope;
	int l2_Nova_i;
	
	if (l0_Nova_direction < 0)
	{
		char l1_Nova_temp;
		
		l1_Nova_temp = l0_Nova_start;
		l0_Nova_start = l0_Nova_end;
		l0_Nova_end = l1_Nova_temp;
	}
	l1_Nova_scope = 0;
	l2_Nova_i = l0_Nova_index;
	for (; l2_Nova_i < l0_Nova_wff->nova_standard_Nova_String_Nova_length && l2_Nova_i >= 0; l2_Nova_i = l2_Nova_i + l0_Nova_direction)
	{
		char l2_Nova_c;
		
		l2_Nova_c = nova_standard_Nova_String_Nova_charAt(l0_Nova_wff, exceptionData, l2_Nova_i);
		if (l2_Nova_c == l0_Nova_start)
		{
			l1_Nova_scope++;
		}
		else if (l2_Nova_c == l0_Nova_end)
		{
			l1_Nova_scope--;
		}
		if (l1_Nova_scope == 0)
		{
			return l2_Nova_i;
		}
	}
	return -1;
}

void nova_standard_math_logic_Nova_WFF_Nova_super(nova_standard_math_logic_Nova_WFF* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_math_logic_Nova_WFF_Nova_letters = (nova_standard_math_logic_Nova_StatementLetter**)nova_null;
	this->prv->nova_standard_math_logic_Nova_WFF_Nova_hypotheses = (nova_standard_datastruct_Nova_ArrayList*)nova_null;
	this->prv->nova_standard_math_logic_Nova_WFF_Nova_conclusion = (nova_standard_math_logic_Nova_Conclusion*)nova_null;
	this->prv->nova_standard_math_logic_Nova_WFF_Nova_wff = (nova_standard_Nova_String*)nova_null;
}

