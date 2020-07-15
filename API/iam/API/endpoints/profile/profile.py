from django.http import HttpResponseNotFound
from django.views.decorators.http import require_http_methods
from .read import readProfile
from .update import updateProfile

@require_http_methods(["GET", "PUT"])
def profile(request):
    if request.method == 'GET':
        return readProfile(request)
    elif request.method == 'PUT':
        return updateProfile(request)
    else:
        return HttpResponseNotFound("Forbidden")
