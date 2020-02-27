package com.example.projectmanager.data.exceptions

import java.lang.Exception
import java.lang.RuntimeException


open class DatabaseException(error: Throwable) : RuntimeException(error)

class EntityNotFoundException(error: Throwable) : DatabaseException(error)