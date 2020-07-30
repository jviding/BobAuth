from django.http import HttpResponseForbidden
from django.views.decorators.http import require_http_methods
from .read import read

@require_http_methods(["GET"])
def users(request):
    if request.method == 'GET':
        return read(request)
    else:
        return HttpResponseForbidden("Method not allowed")
