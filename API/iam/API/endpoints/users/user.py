from django.http import HttpResponseNotFound
from django.views.decorators.http import require_http_methods
from .readOne import readUser
from .update import updateUser
from .delete import deleteUser

@require_http_methods(["GET", "PUT", "DELETE"])
def user(request):
    if request.method == 'GET':
        return readUser(request)
    elif request.method == 'PUT':
        return updateUser(request)
    elif request.method == 'DELETE':
        return deleteUser(request)
    else:
        return HttpResponseNotFound("Forbidden")
