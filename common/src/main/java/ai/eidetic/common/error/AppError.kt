package ai.eidetic.common.error

sealed interface AppError{
    object TextDetectionError: AppError
}