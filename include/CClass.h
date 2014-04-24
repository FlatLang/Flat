//
//  CClass.h
//  CTest
//
//  Created by Braden Steffaniak on 12/27/13.
//  Copyright (c) 2013 Braden Steffaniak. All rights reserved.
//

#ifndef CClass_CClass_h
#define CClass_CClass_h

#include <stdlib.h>
#include <assert.h>

#define call(_OBJ_, _NAME_, ...) _OBJ_->_NAME_(_OBJ_, __VA_ARGS__)

#define NO_PRIVATE\
	static struct Private* new_Private()\
	{\
		return 0;\
	}\
	static void del_Private(struct Private* obj)\
	{\
		\
	}

#define GET_MACRO2P(_1_, _2_, NAME, ...) NAME
#define GET_MACRO3P(_1_, _2_, _3_, NAME, ...) NAME
#define GET_MACRO4P(_1_, _2_, _3_, _4_, NAME, ...) NAME
#define GET_MACRO5P(_1_, _2_, _3_, _4_, _5_, NAME, ...) NAME

#define NEW(...) GET_MACRO3P(__VA_ARGS__, NEW4, NEW3)(__VA_ARGS__)

#define NEW3(_CLASS_, _OBJ_) NEW4(_CLASS_, _OBJ_, _OBJ_->prv = new_Private())

#define NEW4(_CLASS_, _OBJ_, _PRIVATE_)\
	_CLASS_* _OBJ_ = NEW2(_CLASS_);\
	_PRIVATE_

#define NEW2(_CLASS_) (_CLASS_*)malloc(sizeof(_CLASS_))

#define NEW_EXT(_CLASS_, _EXTENDS_, _OBJ_) NEW(_CLASS_, _OBJ_); _OBJ_->getParent = getParent

#define DEL(_OBJ_) DELETE(_OBJ_->prv); DELETE(_OBJ_)

#define DELETE(_OBJ_) free(_OBJ_)

#define FUNC(_TYPE_, _NAME_, ...) _TYPE_ (*_NAME_)(__VA_ARGS__)

#define CLASS(...) GET_MACRO3P(__VA_ARGS__, CLASS3, CLASS2, CLASS1)(__VA_ARGS__);

#define CLASS1(_NAME_) CLASS2(_NAME_,)

#define CLASS2(_NAME_, _BODY_)\
	CLASS_DEF(_NAME_, _BODY_)\
	//DECLARE_METHODS(_NAME_, _METHOD_PREFIX_)

#define CLASS4(_NAME_, _BODY_, _METHOD_PREFIX_)\
	typedef struct _NAME_\
	{\
		_BODY_\
	} _NAME_;\
	//\
	//DECLARE_METHODS(_NAME_, _METHOD_PREFIX_)

#define CLASS_DEF(_NAME_, _BODY_)\
	/*typedef struct _NAME_ _NAME_;*/\
	struct _NAME_\
	{\
		_BODY_\
	};

#define DECLARE_METHODS(_CLASS_, _METHOD_PREFIX_)\
	_METHOD_PREFIX_ _CLASS_* new_##_CLASS_();\
	_METHOD_PREFIX_ void del_##_CLASS_(_CLASS_* obj)

#define PRIVATE(_PRIVATE_VARIABLES_)\
	CLASS4(Private, _PRIVATE_VARIABLES_, static);\
	\
	static Private* new_Private()\
	{\
		INSTANTIATE(Private, p,);\
	}\
	static void del_Private(Private* p)\
	{\
		DELETE(p);\
	}

#define INSTANTIATE(_CLASS_, _OBJ_, _VARIABLE_INITIALIZE_)\
	do\
	{\
		_CLASS_* _OBJ_ = NEW2(_CLASS_);\
		assert(_OBJ_);\
		\
		{\
			_VARIABLE_INITIALIZE_;\
		}\
		\
		return _OBJ_;\
	} while (0)

#endif
