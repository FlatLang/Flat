//
//  CClass.h
//  CTest
//
//  Created by Braden Steffaniak on 12/27/13.
//  Copyright (c) 2013 Braden Steffaniak. All rights reserved.
//

#ifndef CClass_CClass_h
#define CClass_CClass_h

#define USE_GC

#ifdef GC_MALLOC
#	ifdef USE_GC
#		define free GC_FREE
#		define malloc GC_MALLOC
#	else
#		undef GC_MALLOC
#		define GC_MALLOC malloc
#	endif
#endif

#include <stdlib.h>
#include <assert.h>

#define GET_MACRO2P(_1_, _2_, NAME, ...) NAME
#define GET_MACRO3P(_1_, _2_, _3_, NAME, ...) NAME
#define GET_MACRO4P(_1_, _2_, _3_, _4_, NAME, ...) NAME
#define GET_MACRO5P(_1_, _2_, _3_, _4_, _5_, NAME, ...) NAME

#define CCLASS_NEW(...) GET_MACRO3P(__VA_ARGS__, CCLASS_NEW4, CCLASS_NEW3)(__VA_ARGS__)

#define CCLASS_NEW3(_CLASS_, _OBJ_) CCLASS_NEW4(_CLASS_, _OBJ_, _OBJ_->prv = new_Private())

#define CCLASS_NEW4(_CCLASS_CLASS_, _OBJ_, _CCLASS_PRIVATE_)\
	_CCLASS_CLASS_* _OBJ_ = CCLASS_NEW2(_CCLASS_CLASS_);\
	_CCLASS_PRIVATE_

#define CCLASS_NEW2(_CLASS_) (_CLASS_*)GC_MALLOC(sizeof(_CLASS_))

#define CCLASS_NEW_EXT(_CLASS_, _EXTENDS_, _OBJ_) CCLASS_NEW(_CLASS_, _OBJ_); _OBJ_->getParent = getParent

#define CCLASS_DEL(_OBJ_) CCLASS_DELETE(_OBJ_->prv); CCLASS_DELETE(_OBJ_)

#define CCLASS_DELETE(_OBJ_) _OBJ_ = NULL//free(_OBJ_)

#define CCLASS_FUNC(_TYPE_, _NAME_, ...) _TYPE_ (*_NAME_)(__VA_ARGS__)

#define CCLASS_CLASS(...) GET_MACRO3P(__VA_ARGS__, CCLASS_CLASS3, CCLASS_CLASS2, CCLASS_CLASS1)(__VA_ARGS__);

#define CCLASS_CLASS1(_NAME_) CCLASS_CLASS2(_NAME_,)

#define CCLASS_CLASS2(_NAME_, _BODY_)\
	CCLASS_CLASS_DEF(_NAME_, _BODY_)\
	//CCLASS_DECLARE_METHODS(_NAME_, _METHOD_PREFIX_)

#define CCLASS_CLASS4(_NAME_, _BODY_, _METHOD_PREFIX_)\
	typedef struct _NAME_\
	{\
		_BODY_\
	} _NAME_;\
	//\
	//CCLASS_DECLARE_METHODS(_NAME_, _METHOD_PREFIX_)

#define CCLASS_CLASS_DEF(_NAME_, _BODY_)\
	/*typedef struct _NAME_ _NAME_;*/\
	struct _NAME_\
	{\
		_BODY_\
	};

#define CCLASS_DECLARE_METHODS(_CLASS_, _METHOD_PREFIX_)\
	_METHOD_PREFIX_ _CLASS_* new_##_CLASS_();\
	_METHOD_PREFIX_ void del_##_CLASS_(_CLASS_* obj)

#define CCLASS_PRIVATE(_CCLASS_PRIVATE_VARIABLES_)\
	CCLASS_CLASS4(Private, _CCLASS_PRIVATE_VARIABLES_, static);\
	\
	static Private* new_Private()\
	{\
		CCLASS_INSTANTIATE(Private, p,);\
	}\
	static void del_Private(Private* p)\
	{\
		CCLASS_DELETE(p);\
	}

#define CCLASS_INSTANTIATE(_CLASS_, _OBJ_, _VARIABLE_INITIALIZE_)\
	do\
	{\
		_CLASS_* _OBJ_ = CCLASS_NEW2(_CLASS_);\
		assert(_OBJ_);\
		\
		{\
			_VARIABLE_INITIALIZE_;\
		}\
		\
		return _OBJ_;\
	} while (0)

#endif
