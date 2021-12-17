$('.contents-wrapper').fadeIn(1000);


function imagerotate(ro) {
	cropper = new Cropper(image, {
		aspectRatio: 1,
		viewMode: 1,
		viewMode: 1,
		rotate: -90
	});
}

window.addEventListener('DOMContentLoaded', function () {
	var avatar = document.getElementById('avatar');
	var image = document.getElementById('image');
	var input = document.getElementById('input');
	var $progress = $('.aprogress');
	var $progressBar = $('.aprogress-bar');
	var $alert = $('.alert');
	var $modal = $('#modal1');
	var cropper;

	input.addEventListener('change', function (e) {
		var files = e.target.files;
		var done = function (url) {
			input.value = '';
			image.src = url;
			$alert.hide();
			$modal.modal('show');
		};
		var reader;
		var file;
		var url;

		if (files && files.length > 0) {
			file = files[0];

			if (URL) {
				done(URL.createObjectURL(file));
			} else if (FileReader) {
				reader = new FileReader();
				reader.onload = function (e) {
					done(reader.result);
				};
				reader.readAsDataURL(file);
			}
		}
	});

	document.getElementById('rrotateImg').addEventListener('click', function () {
		cropper.rotate(-90);
	});

	$modal.on('shown.bs.modal', function () {
		cropper = new Cropper(image, {
			aspectRatio: 1,
			viewMode: 1,
		});
	}).on('hidden.bs.modal', function () {
		cropper.destroy();
		cropper = null;
	});

	document.getElementById('crop').addEventListener('click', function () {
		var initialAvatarURL;
		var canvas;

		$modal.modal('hide');

		if (cropper) {
			canvas = cropper.getCroppedCanvas({
				width: 80,
				height: 80,
			});

			initialAvatarURL = avatar.src;
			avatar.src = canvas.toDataURL();
			$progress.hide();
			$alert.removeClass('alert-success alert-warning');
			canvas.toBlob(function (blob) {
				var formData = new FormData();

				formData.append('photo', blob, "blob.png");

				$.ajax('/member/uploadProfileImg_n', {
					method: 'POST',
					data: formData,
					processData: false,
					contentType: false,

					xhr: function () {
						var xhr = new XMLHttpRequest();

						xhr.upload.onprogress = function (e) {
							var percent = '0';
							var percentage = '0%';

							if (e.lengthComputable) {
								percent = Math.round((e.loaded / e.total) * 100);
								percentage = percent + '%';
								$progressBar.width(percentage).attr('aria-valuenow', percent).text(percentage);
							}
						};

						return xhr;
					},

					success: function (result) {
						$alert.show().addClass('alert-success').text('Upload success');
						$alert.hide();
					},

					error: function () {
						avatar.src = initialAvatarURL;
						$alert.show().addClass('alert-warning').text('Upload error');
						$alert.hide();
					},

					complete: function () {
						$progress.hide();
					},
				});
			});
		}
	});
});
