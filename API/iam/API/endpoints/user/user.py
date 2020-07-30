from django.http import HttpResponseForbidden
from django.views.decorators.http import require_http_methods
from .update import update
from .delete import delete

@require_http_methods(["PUT", "DELETE"])
def user(request):
    if request.method == "PUT":
        return update(request)
    elif request.method == "DELETE":
        return delete(request)
    else:
        return HttpResponseForbidden("Method not allowed")
